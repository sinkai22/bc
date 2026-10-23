const crypto = require("crypto");

class HeliumWallet {
    constructor() {
        this.privateKey = this.generatePrivateKey();
        this.publicKey = this.generatePublicKey(this.privateKey);
        this.address = this.generateAddress(this.publicKey);
        this.transactions = [];
        this.balance = 900000;
    }

    generatePrivateKey() {
        return crypto.randomBytes(32).toString("hex");
    }

    generatePublicKey(privateKey) {
        return crypto
            .createHash("sha256")
            .update(privateKey)
            .digest("hex");
    }

    generateAddress(publicKey) {
        return publicKey.substring(0, 10);
    }

    sendTokens(receiverAddress, amount) {
        if (amount <= this.balance) {
            this.balance -= amount;

            this.transactions.push({
                sender: this.address,
                receiver: receiverAddress,
                amount: amount
            });

            return true;
        }

        return false;
    }

    getBalance() {
        return this.balance;
    }

    getTransactionHistory() {
        return this.transactions;
    }
}

// Example Usage

const myWallet = new HeliumWallet();

console.log("Wallet Address:", myWallet.address);
console.log("Balance:", myWallet.getBalance());

const sendSuccess = myWallet.sendTokens(
    "receiver_address",
    900000
);

if (sendSuccess) {
    console.log("Tokens sent successfully!");
} else {
    console.log("Insufficient Balance!");
}

console.log("New Balance:", myWallet.getBalance());
console.log("Transaction History:");
console.log(myWallet.getTransactionHistory());