# Mark Myers, student ID 001254824
# PyCharm 2021.2.2 (Community Edition)

import datetime
import package
import hashtable
import distance

truck1 = hashtable.ChainingHashTable()  # Create hash table for truck 1
truck2 = hashtable.ChainingHashTable()  # Create hash table for truck 2
deliveredList = hashtable.ChainingHashTable()  # Create hash table for packages that have been delivered


totalMileage = 0.0  # Track total miles driven
truck1Time = datetime.datetime(2021, 12, 4, 8)  # The work day starts at 8:00 AM
truck2Time = datetime.datetime(2021, 12, 4, 8)  # The work day starts at 8:00 AM


package.Package.loadPackageData()  # Call loadPackageData to bring in package data from CSV file
distance.loadDistanceData()  # Call LoadDistanceData to bring in package date from CSV file
pack = package.packageTable  # Use 'pack' to shorten code rather than calling package.packageTable


# Runtime of O(N^2) due to the method has nested loops, and it has to search to find appropriate packages to load.
# loadTruck takes a truck hash table and a truck number as parameters.  Some packages can only be shipped on truck 2.
# Other packages have to ship together.  Also, a plane carrying packages has been delayed and packages will not
# arrive at the HUB until 9:05 AM.  loadTruck uses a self-adjusting greedy algorithm to figure out which packages need
# to be loaded.
def loadTruck(truckList, truckNum):
    index = 0  # Used to ensure no more than 16 packages are on a truck at a time
    planeArrival = datetime.time(9, 5)  # Package arrival to the HUB from the delayed flight is 9:05 AM

    # Self-adjusting greedy algorithm used to check what packages can be loaded onto the truck.  Checks to make sure
    # that packages that need to be loaded onto truck 2 are loaded correctly, loads the packages that must ship
    # together, and checks for priority delivery.
    if not hashtable.ChainingHashTable.isEmpty(pack):  # Check to make sure that there are packages to load.
        if truckNum == 2:
            for list in pack.table:
                for i in list:  # Loads packages that can only be loaded on truck 2.
                    if 'Can only be on truck 2' in package.Package.getNotes(pack, i[0]):
                        hashtable.ChainingHashTable.updateStatus(pack, i[0], 'Out for Delivery')
                        truckList.insert(i[0], i[1])
                        pack.remove(i[0])
                        index = index + 1

        for list in pack.table:
            for i in list:  # Packages #13, #14, #15. #16, #19, and #20 must go together.
                if i[0] == 13 or i[0] == 14 or i[0] == 15 or i[0] == 16 or i[0] == 19 or i[0] == 20:
                    hashtable.ChainingHashTable.updateStatus(pack, i[0], 'Out for Delivery')
                    truckList.insert(i[0], i[1])
                    pack.remove(i[0])
                    index = index + 1

        for list in pack.table:
            for i in list:  # Loads priority packages onto the truck.
                if not 'EOD' in package.Package.getDeadline(pack, i[0]) and i[0] != 6 and i[0] != 25:
                    hashtable.ChainingHashTable.updateStatus(pack, i[0], 'Out for Delivery')
                    truckList.insert(i[0], i[1])
                    pack.remove(i[0])
                    index = index + 1
                if index == 16:
                    return
        for list in pack.table:
            keyList = []  # keyList is used to keep indexes of pack the same until the loop stops
            for i in list:  # Checks for if packages have been delayed on the flight.  If it is after planeArrival time,
                            # loads the packages, otherwise it skips them.
                if 'Delayed on flight' in package.Package.getNotes(pack, i[0]):
                    if truckNum == 1 and truck1Time.time() >= planeArrival:
                        hashtable.ChainingHashTable.updateStatus(pack, i[0], 'Out for Delivery')
                        truckList.insert(i[0], i[1])
                        keyList.append(i[0])
                        index = index + 1
                    elif truckNum == 2 and truck2Time.time() >= planeArrival:
                        hashtable.ChainingHashTable.updateStatus(pack, i[0], 'Out for Delivery')
                        truckList.insert(i[0], i[1])
                        keyList.append(i[0])
                        index = index + 1
                if index == 16:
                    break
            for i in keyList:  # Remove packages in keyList from pack
                pack.remove(i)

        for list in pack.table:
            keyList = []  # keyList is used to keep indexes of pack the same until the loop stops
            for i in list:  # Load the remaining packages that weren't delayed on the flight.
                if not 'Delayed on flight' in package.Package.getNotes(pack, i[0]):
                    hashtable.ChainingHashTable.updateStatus(pack, i[0], 'Out for Delivery')
                    truckList.insert(i[0], i[1])
                    keyList.append(i[0])
                    index = index + 1
                if index == 16:
                    break
            for i in keyList:  # Remove packages in keyList from pack
                pack.remove(i)
            if index == 16:
                return


# Runtime of O(N^2) due to the potential of the method having to loop multiple times before all packages are delivered.
# deliverPackages takes the loaded truck hash table and truck number and delivers the packages to the addresses.
# Time for each truck is updated by calculating distance and speed of the truck.  A list containing the delivered
# packages is updated for each packaged with the delivered time.  Package 9 has an address change at 10:20 AM, and the
# package is delivered accordingly.
def deliverPackages(truck, truckNum):
    currentAddress = 'HUB'  # Package delivery will always start at the HUB
    global totalMileage
    global truck1Time
    global truck2Time

    while not truck.isEmpty():  # Loop through until all of the packages have been delivered.
        if truckNum == 1:  # Uses the truck number to track the individual times per truck.
            for list in truck.table:
                if list != []:  # Check to make sure there is information to utilize rather than an empty list.
                    nextAddress = distance.minDistanceFrom(currentAddress, truck)

                    for packageToDeliver in list:
                        # Update package 9's address
                        if nextAddress == package.Package.getAddress(truck, packageToDeliver[0]) and \
                                packageToDeliver[0] == 9 and truck1Time.time() >= datetime.time(10, 20):
                            hashtable.ChainingHashTable.updateAddress(truck, packageToDeliver[0], '410 S State St')
                            nextAddress = package.Package.getAddress(truck, packageToDeliver[0])
                            totalMileage = totalMileage + float(distance.distanceBetween(currentAddress, nextAddress))
                            timeChange = datetime.timedelta(hours=float(distance.distanceBetween(currentAddress, nextAddress)) / 18)
                            truck1Time = truck1Time + timeChange
                            hashtable.ChainingHashTable.updateStatus(truck, packageToDeliver[0], 'Delivered at ' + str(truck1Time))
                            deliveredList.insert(packageToDeliver[0], packageToDeliver[1])
                            hashtable.ChainingHashTable.remove(truck, packageToDeliver[0])
                            currentAddress = nextAddress
                        elif nextAddress == package.Package.getAddress(truck, packageToDeliver[0]):
                            totalMileage = totalMileage + float(distance.distanceBetween(currentAddress, nextAddress))
                            timeChange = datetime.timedelta(hours=float(distance.distanceBetween(currentAddress, nextAddress)) / 18)
                            truck1Time = truck1Time + timeChange
                            hashtable.ChainingHashTable.updateStatus(truck, packageToDeliver[0], 'Delivered at ' + str(truck1Time))
                            deliveredList.insert(packageToDeliver[0], packageToDeliver[1])
                            hashtable.ChainingHashTable.remove(truck, packageToDeliver[0])
                            currentAddress = nextAddress

        elif truckNum == 2:
            for list in truck.table:
                if list != []:
                    nextAddress = distance.minDistanceFrom(currentAddress, truck)

                    for packageToDeliver in list:
                        if nextAddress == package.Package.getAddress(truck, packageToDeliver[0]) and \
                                packageToDeliver[0] == 9 and truck2Time.time() >= datetime.time(10, 20):
                            hashtable.ChainingHashTable.updateAddress(truck, packageToDeliver[0], '410 S State St')
                            nextAddress = package.Package.getAddress(truck, packageToDeliver[0])
                            totalMileage = totalMileage + float(distance.distanceBetween(currentAddress, nextAddress))
                            timeChange = datetime.timedelta(hours=float(distance.distanceBetween(currentAddress, nextAddress)) / 18)
                            truck2Time = truck2Time + timeChange
                            hashtable.ChainingHashTable.updateStatus(truck, packageToDeliver[0], 'Delivered at ' + str(truck2Time))
                            deliveredList.insert(packageToDeliver[0], packageToDeliver[1])
                            hashtable.ChainingHashTable.remove(truck, packageToDeliver[0])
                            currentAddress = nextAddress
                        elif nextAddress == package.Package.getAddress(truck, packageToDeliver[0]):
                            totalMileage = totalMileage + float(distance.distanceBetween(currentAddress, nextAddress))
                            timeChange = datetime.timedelta(hours=float(distance.distanceBetween(currentAddress, nextAddress)) / 18)
                            truck2Time = truck2Time + timeChange
                            hashtable.ChainingHashTable.updateStatus(truck, packageToDeliver[0], 'Delivered at ' + str(truck2Time))
                            deliveredList.insert(packageToDeliver[0], packageToDeliver[1])
                            hashtable.ChainingHashTable.remove(truck, packageToDeliver[0])
                            currentAddress = nextAddress

    # Send the first truck back to the HUB for more packages.
    if not hashtable.ChainingHashTable.isEmpty(pack) and truckNum == 1:
        totalMileage = totalMileage + float(distance.distanceBetween(currentAddress, 'HUB'))


# Runtime of O(n^2) due to that there could be an increase of packages in the future.
# userInterface takes the user selection and return the appropriate data based on the selection of the user.
# A user can view the total miles travelled(option 1), view individual packages(option 2), view packages by
# address(option 3), view packages by city(option 4), view packages by zip code(option 5), view packages by
# weight(option 6), view packaged by deadline(option 7), or view packages by delivery status(option 8).
def userInterface(selection):
    if selection == 1:  # View total mileage.
        print('\nTotal mileage: ', round(totalMileage, 1), '\n')
    elif selection == 2:  # Search by package number.
        packageID = int(input('Please enter a package number. '))
        print()
        print(hashtable.ChainingHashTable.search(deliveredList, packageID),'\n')
    elif selection == 3:  # Search by package address.
        packageAddress = input('Please enter an address. ')
        print()
        for list in deliveredList.table:
            for i in list:
                if packageAddress in package.Package.getAddress(deliveredList, i[0]):
                    print(hashtable.ChainingHashTable.search(deliveredList, i[0]))
        print()
    elif selection == 4:  # Search by package city.
        packageCity = input('Please enter a city. ')
        print()
        for list in deliveredList.table:
            for i in list:
                if packageCity in package.Package.getCity(deliveredList, i[0]):
                    print(hashtable.ChainingHashTable.search(deliveredList, i[0]))
        print()
    elif selection == 5:  # Search by package zip code.
        packageZip = input('Please enter a zip code. ')
        print()
        for list in deliveredList.table:
            for i in list:
                if packageZip == package.Package.getZip(deliveredList, i[0]):
                    print(hashtable.ChainingHashTable.search(deliveredList, i[0]))
        print()
    elif selection == 6:  # Search by package weight.
        packageWeight = input('Please enter a weight. ')
        print()
        for list in deliveredList.table:
            for i in list:
                if packageWeight == package.Package.getWeight(deliveredList, i[0]):
                    print(hashtable.ChainingHashTable.search(deliveredList, i[0]))
        print()
    elif selection == 7:  # Search by packaged deadline.
        print()
        print('1: 9:00 AM')
        print('2: 10:30 AM')
        print('3: End of Day')
        packageDeadline = int(input('Pleasde select a deadline. '))
        print()
        for list in deliveredList.table:
            for i in list:
                if packageDeadline == 1:
                    if '9:00 AM' in package.Package.getDeadline(deliveredList, i[0]):
                        print(hashtable.ChainingHashTable.search(deliveredList, i[0]))
                elif packageDeadline == 2:
                    if '10:30 AM' in package.Package.getDeadline(deliveredList, i[0]):
                        print(hashtable.ChainingHashTable.search(deliveredList, i[0]))
                elif packageDeadline == 3:
                    if 'EOD' in package.Package.getDeadline(deliveredList, i[0]):
                        print(hashtable.ChainingHashTable.search(deliveredList, i[0]))
        print()
    elif selection == 8:  # Search by package delivery status.
        print()
        print('1: At the HUB')
        print('2: Out for Delivery')
        print('3: Delivered')
        packageStatus = int(input('Please select a status. '))
        print()
        for list in deliveredList.table:
            for i in list:
                if packageStatus == 1:
                    if 'At the HUB' in package.Package.getStatus(deliveredList, i[0]):
                        print(hashtable.ChainingHashTable.search(deliveredList, i[0]))
                elif packageStatus == 2:
                    if 'Out for Delivery' in package.Package.getStatus(deliveredList, i[0]):
                        print(hashtable.ChainingHashTable.search(deliveredList, i[0]))
                elif packageStatus == 3:
                    if 'Delivered' in package.Package.getStatus(deliveredList, i[0]):
                        print(hashtable.ChainingHashTable.search(deliveredList, i[0]))
        print()


loadTruck(truck1, 1)
loadTruck(truck2, 2)
deliverPackages(truck1, 1)
deliverPackages(truck2, 2)
loadTruck(truck1, 1)
deliverPackages(truck1, 1)


i = -1
while i != 0:  # Keep the interface running until exit is selected.
    print('***WGUPS Delivery Program Interface***')
    print('1: View total miles')
    print('2: Search by package ID')
    print('3: Search by address')
    print('4: Search by city')
    print('5: Search by zip code')
    print('6: Search by weight')
    print('7: Search by deadline')
    print('8: Search by delivery status')
    print('0: Exit')
    print()
    i = int(input('Please make a selection. '))
    if i != 0:
        userInterface(i)