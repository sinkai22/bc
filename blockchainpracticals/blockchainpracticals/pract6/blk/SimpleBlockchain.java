package blk;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimpleBlockchain {

    static class Block {
        int index;
        long timestamp;
        String previousHash;
        String data;
        String hash;

        public Block(int index, long timestamp, String data, String previousHash) {
            this.index = index;
            this.timestamp = timestamp;
            this.data = data;
            this.previousHash = previousHash;
            this.hash = calculateHash();
        }

        private String calculateHash() {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");

                String input = index +
                               Long.toString(timestamp) +
                               data +
                               previousHash;

                byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));

                StringBuilder hexString = new StringBuilder();

                for (byte b : hashBytes) {
                    String hex = Integer.toHexString(0xff & b);

                    if (hex.length() == 1) {
                        hexString.append('0');
                    }

                    hexString.append(hex);
                }

                return hexString.toString();

            } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {

        List<Block> blockchain = new ArrayList<>();

        // Genesis Block
        Block genesisBlock = new Block(
                0,
                System.currentTimeMillis(),
                "Genesis Block",
                "0"
        );

        blockchain.add(genesisBlock);

        // Add Block 1
        Block block1 = new Block(
                1,
                System.currentTimeMillis(),
                "Transaction Data 1",
                genesisBlock.hash
        );

        blockchain.add(block1);

        // Add Block 2
        Block block2 = new Block(
                2,
                System.currentTimeMillis(),
                "Transaction Data 2",
                block1.hash
        );

        blockchain.add(block2);

        // Display Blockchain
        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("===== SIMPLE BLOCKCHAIN =====\n");

        for (Block block : blockchain) {

            System.out.println("Block Number : " + block.index);
            System.out.println("Timestamp    : " + block.timestamp);
            System.out.println("Date         : "
                    + sdf.format(new Date(block.timestamp)));
            System.out.println("Data         : " + block.data);
            System.out.println("Previous Hash: "
                    + block.previousHash);
            System.out.println("Hash         : "
                    + block.hash);

            System.out.println("-------------------------------------------");
        }
    }
}