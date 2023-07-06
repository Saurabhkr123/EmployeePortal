package com.employee.portal.service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptionUtils {


    public static void main(String[] args) throws Exception {

        String password = "your_password";
        String plaintext = "Hello, World!";

        String encryptedText = encryptAES(plaintext, password);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = decryptAES(encryptedText, password);
        System.out.println("Decrypted Text: " + decryptedText);

    }



    // AES encryption function
    public static String encryptAES(String text, String password) throws Exception {
        byte[] key = password.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // AES decryption function
    public static String decryptAES(String encryptedText, String password) throws Exception {
        byte[] key = password.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }




}

