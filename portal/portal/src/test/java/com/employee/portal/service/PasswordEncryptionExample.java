package com.employee.portal.service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class PasswordEncryptionExample {

    private static final String AES_ALGORITHM = "AES";

    public static void main(String[] args) {
        String password = "Passwordabcd@1K2";

        String encryptedPassword = encryptPassword(password);
        System.out.println("Encrypted Password: " + encryptedPassword);

        String decryptedPassword = decryptPassword(encryptedPassword);
        System.out.println("Decrypted Password: " + decryptedPassword);
    }

    // Method to encrypt a password using AES
    public static String encryptPassword(String password) {
        try {
            SecretKeySpec secretKey = generateSecretKey();
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to decrypt an encrypted password using AES
    public static String decryptPassword(String encryptedPassword) {
        try {
            SecretKeySpec secretKey = generateSecretKey();
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to generate a secret key spec for AES encryption/decryption
    private static SecretKeySpec generateSecretKey() throws NoSuchAlgorithmException {
        // Provide your own secret key here (must be a valid length: 16, 24, or 32 bytes)
        String secretKeyString = "mySecretKey123456789";
        byte[] secretKeyBytes = secretKeyString.getBytes(StandardCharsets.UTF_8);

        // Use a hashing algorithm to generate a valid key length
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        secretKeyBytes = sha.digest(secretKeyBytes);
        secretKeyBytes = Arrays.copyOf(secretKeyBytes, 16); // Use only the first 16 bytes for AES-128

        return new SecretKeySpec(secretKeyBytes, AES_ALGORITHM);
    }
}
