import hashlib
import datetime

class Block:
    def __init__(self, index, data, previous_hash):
        self.index = index
        self.timestamp = str(datetime.datetime.now())
        self.data = data
        self.previous_hash = previous_hash
        self.hash = self.calculate_hash()

    def calculate_hash(self):
        value = str(self.index) + self.timestamp + self.data + self.previous_hash
        return hashlib.sha256(value.encode()).hexdigest()

# Create the genesis block
block1 = Block(1, "Genesis Block", "0")

print("Index:", block1.index)
print("Date:", block1.timestamp)
print("Data:", block1.data)
print("Hash:", block1.hash)
