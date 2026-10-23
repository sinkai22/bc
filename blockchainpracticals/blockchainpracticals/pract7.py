import hashlib
import ecdsa
import binascii
from Crypto.Hash import RIPEMD160


def generate_wallet():
    private_key = ecdsa.SigningKey.generate(curve=ecdsa.SECP256k1)
    public_key = private_key.get_verifying_key()
    return private_key, public_key


def create_address(public_key):
    sha256_pk = hashlib.sha256(public_key.to_string()).digest()

    ripemd160 = RIPEMD160.new()
    ripemd160.update(sha256_pk)
    hashed_pk = ripemd160.digest()

    versioned_pk = b'\x00' + hashed_pk

    checksum = hashlib.sha256(
        hashlib.sha256(versioned_pk).digest()
    ).digest()[:4]

    address = versioned_pk + checksum

    return binascii.hexlify(address).decode('utf-8')


def hash_transaction(message):
    return hashlib.sha256(
        message.encode('utf-8')
    ).hexdigest()


def sign_transaction(private_key, transaction_hash):
    signature = private_key.sign(
        transaction_hash.encode('utf-8')
    )

    return binascii.hexlify(signature).decode('utf-8')


def verify_transaction(public_key, signature, transaction_hash):
    try:
        return public_key.verify(
            binascii.unhexlify(signature),
            transaction_hash.encode('utf-8')
        )
    except ecdsa.BadSignatureError:
        return False


def simulate_transaction():

    print("Generating Wallets...\n")

    sender_private_key, sender_public_key = generate_wallet()
    receiver_private_key, receiver_public_key = generate_wallet()

    sender_address = create_address(sender_public_key)
    receiver_address = create_address(receiver_public_key)

    print("Sender Address:")
    print(sender_address)

    print("\nReceiver Address:")
    print(receiver_address)

    transaction_data = (
        f"Sender: {sender_address}, "
        f"Receiver: {receiver_address}, "
        f"Amount: 0.5 BTC"
    )

    transaction_hash = hash_transaction(transaction_data)

    print("\nTransaction Data:")
    print(transaction_data)

    print("\nTransaction Hash:")
    print(transaction_hash)

    signature = sign_transaction(
        sender_private_key,
        transaction_hash
    )

    print("\nDigital Signature:")
    print(signature)

    is_valid = verify_transaction(
        sender_public_key,
        signature,
        transaction_hash
    )

    print("\nTransaction Verification:")
    print(is_valid)


simulate_transaction()
