# The ChainingHashTable class creates hash tables, inserts information into the hash table, searches for information,
# and removes information.  It can also update the status of the packages, updates the addresses, and can tell if a
# hash table is empty.
# Reference: ZyBooks C950: Data Structures and Algorithms II, figure 7.8.2
# Reference: WGU Webinar "Let's Go Hashing" by Dr. Cemal Tepe
class ChainingHashTable:
    # Creates the hash table.
    def __init__(self, hashLength = 10):
        self.table = []
        for i in range(hashLength):
            self.table.append([])

    # Inserts information into the hash table.
    def insert(self, packageID, package):
        bucket = hash(packageID) % len(self.table)
        bucketList = self.table[bucket]

        item = [packageID, package]
        bucketList.append(item)

    # Searches for information in the hash table by packageID.
    def search(self, packageID):
        bucket = hash(packageID) % len(self.table)
        bucketList = self.table[bucket]

        for key in bucketList:
            if key[0] == packageID:
                return key[1]
        return None

    # Removes a package from the hash table by packageID.
    def remove(self, packageID):
        bucket = hash(packageID) % len(self.table)
        bucketList = self.table[bucket]

        for item in bucketList:
            if item[0] == packageID:
                bucketList.remove([item[0], item[1]])

    # Updates the delivery status of the package given in the parameters.
    def updateStatus(self, packageID, status):
        bucket = hash(packageID) % len(self.table)
        bucketList = self.table[bucket]

        for key in bucketList:
            if key[0] == packageID:
                key[1].status = status

    # Updates the address of the package given in the parameters.
    def updateAddress(self, packageID, address):
        bucket = hash(packageID) % len(self.table)
        bucketList = self.table[bucket]

        for key in bucketList:
            if key[0] == packageID:
                key[1].address = address

    # Check to see if there is information in the hash table or if it is empty.
    def isEmpty(self):
        for item in self.table:
            if item != []:
                return False
        return True