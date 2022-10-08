import csv
import package

distanceData = []


# Runtime of O(N) due to a loop.
# Retrieve the distance information from the CSV file and load it into the list.
def loadDistanceData():
    fileLocation = 'WGUPS Distance Table.csv'

    with open(fileLocation) as distances:
        distData = csv.reader(distances, delimiter=',')
        for i in distData:
            distanceData.append(i)


# Runtime of O(N) due to a loop.
# Calculate the distance between the addresses given as parameters.
def distanceBetween(address1, address2):
    address1Index = 0
    address2Index = 0

    for address in range(0, len(distanceData)):
        if address1 in distanceData[address][1]:
            address1 = distanceData[address][0]
        if address2 in distanceData[address][1]:
            address2 = distanceData[address][0]
    for i in range(0, len(distanceData[0])):
        if distanceData[0][i] == address1:
            address1Index = i
            break

    for j in range(0, len(distanceData)):
        if distanceData[j][0] == address2:
            address2Index = j
            break

    if distanceData[address2Index][address1Index] == '':
        return distanceData[address1Index - 1][address2Index + 1]
    else:
        return distanceData[address2Index][address1Index]


# Runtime of O(N^2) due to the nested loops.
# Calculate the closest address to the fromAddress to the remaining addresses in packages.
def minDistanceFrom(fromAddress, packages):
    minDistance = 9999
    minAddress = ''

    for list in packages.table:
        for address in list:
            currentDistance = float(distanceBetween(fromAddress, package.Package.getAddress(packages, address[0])))

            if currentDistance < minDistance:
                minDistance = currentDistance
                minAddress = package.Package.getAddress(packages, address[0])

    return minAddress