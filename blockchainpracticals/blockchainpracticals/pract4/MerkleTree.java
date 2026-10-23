import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class MerkleTree {

    // Generate SHA-256 hash
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(input.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Build Merkle Tree and return root
    public static String getMerkleRoot(List<String> transactions) {

        if (transactions.size() == 0) {
            return "";
        }

        List<String> currentLevel = new ArrayList<>();

        System.out.println("Transaction Hashes:");

        for (String tx : transactions) {
            String hash = applySha256(tx);
            currentLevel.add(hash);

            System.out.println(tx + " -> " + hash);
        }

        while (currentLevel.size() > 1) {

            List<String> nextLevel = new ArrayList<>();

            System.out.println("\nNew Level:");

            for (int i = 0; i < currentLevel.size(); i += 2) {

                String left = currentLevel.get(i);

                String right;

                if (i + 1 < currentLevel.size()) {
                    right = currentLevel.get(i + 1);
                } else {
                    right = left; // duplicate last hash
                }

                String combinedHash = applySha256(left + right);

                System.out.println(
                        left.substring(0, 8) + "... + "
                      + right.substring(0, 8) + "... -> "
                      + combinedHash.substring(0, 16) + "..."
                );

                nextLevel.add(combinedHash);
            }

            currentLevel = nextLevel;
        }

        return currentLevel.get(0);
    }

    public static void main(String[] args) {

        List<String> transactions = new ArrayList<>();

        transactions.add("TX1");
        transactions.add("TX2");
        transactions.add("TX3");
        transactions.add("TX4");
        transactions.add("TX5");

        String merkleRoot = getMerkleRoot(transactions);

        System.out.println("\n=================================");
        System.out.println("Merkle Root:");
        System.out.println(merkleRoot);
        System.out.println("=================================");
    }
}
