import csv
import hashtable

packageTable = hashtable.ChainingHashTable()  # Create package table to load package data into from CSV file.


# The Package class creates instances of the packages, loads the data into the hash table, and has getters to
# use the information for loading and delivering.
# Reference: WGU Webinar "Getting Greedy, who moved my data" by Dr. Cemal Tepe
class Package:
    # Create an instance of Package.
    def __init__(self, id, address, city, state, zip, deadline, weight, notes, status):
        self.id = id
        self.address = address
        self.city = city
        self.state = state
        self.zip = zip
        self.deadline = deadline
        self.weight = weight
        self.notes = notes
        self.status = status

    # Format information as strings.
    def __str__(self):
        return "%s, %s, %s, %s, %s, %s, %s, %s, %s" % (self.id, self.address, self.city, self.state, self.zip,
                                                   self.deadline, self.weight, self.notes, self.status)

    # Return the address.
    def getAddress(self, id):
        return hashtable.ChainingHashTable.search(self, id).address

    # Return the city.
    def getCity(self, id):
        return hashtable.ChainingHashTable.search(self, id).city

    # Return the zip.
    def getZip(self, id):
        return hashtable.ChainingHashTable.search(self, id).zip

    # Return the deadline.
    def getDeadline(self, id):
        return hashtable.ChainingHashTable.search(self, id).deadline

    # Return the weight.
    def getWeight(self, id):
        return hashtable.ChainingHashTable.search(self, id).weight

    # Return the notes.
    def getNotes(self, id):
        return hashtable.ChainingHashTable.search(self, id).notes

    # Return the status.
    def getStatus(self, id):
        return hashtable.ChainingHashTable.search(self, id).status

    # Runtime of O(N)
    # Retrieve the package information from the CSV file and call the constructor to create an instance.
    # Reference: WGU Webinar "Getting Greedy, who moved my data" by Dr. Cemal Tepe
    def loadPackageData():
        fileLocation = 'WGUPS Package File.csv'

        with open(fileLocation) as packages:
            packageData = csv.reader(packages, delimiter=',')
            next(packageData)
            for package in packageData:
                packageID = int(package[0])
                packageAddress = package[1]
                packageCity = package[2]
                packageState = package[3]
                packageZip = package[4]
                packageDeadline = package[5]
                packageWeight = package[6]
                packageNotes = package[7]
                deliveryStatus = 'At the HUB'

                newPackage = Package(packageID, packageAddress, packageCity, packageState, packageZip, packageDeadline,
                                  packageWeight, packageNotes, deliveryStatus)

                packageTable.insert(int(package[0]), newPackage)