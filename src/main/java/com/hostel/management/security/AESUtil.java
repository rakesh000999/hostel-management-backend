package com.hostel.management.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESUtil {

    private static final String ALGORITHM = "AES";

    // Generate AES-256 key
    public static SecretKey generateKey() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
        generator.init(256, new SecureRandom());
        return generator.generateKey();
    }

    // Encrypt file bytes
    public static byte[] encrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    // Decrypt file bytes
    public static byte[] decrypt(byte[] encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }

    // Convert key to bytes
    public static byte[] keyToBytes(SecretKey key) {
        return key.getEncoded();
    }

    // Restore key from bytes
    public static SecretKey bytesToKey(byte[] keyBytes) {
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }
}
