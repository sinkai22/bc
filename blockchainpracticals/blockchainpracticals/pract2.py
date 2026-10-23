import hashlib
import datetime

class Block:
    def __init__(self, index, transactions, previous_hash):
        self.index = index
        self.timestamp = str(datetime.datetime.now())
        self.transactions = transactions
        self.previous_hash = previous_hash
        self.nonce = 0
        self.hash = self.calculate_hash()

    def calculate_hash(self):
        # Concatenate block attributes into a single string
        block_data = (
            str(self.index) +
            self.timestamp +
            str(self.transactions) +
            str(self.previous_hash) +
            str(self.nonce)
        )

        # Generate SHA-256 hash
        return hashlib.sha256(block_data.encode()).hexdigest()

    def display(self):
        print("-" * 40)
        print("Block Number :", self.index)
        print("Timestamp    :", self.timestamp)
        print("Transactions :", self.transactions)
        print("Previous Hash:", self.previous_hash)
        print("Nonce        :", self.nonce)
        print("Current Hash :", self.hash)
        print("-" * 40)

# Genesis Block
block1 = Block(1, ["Alice pays Bob 10 BTC"], "0")
block1.display()

# Second Block
block2 = Block(2, ["Bob pays Charlie 5 BTC"], block1.hash)
block2.display()
