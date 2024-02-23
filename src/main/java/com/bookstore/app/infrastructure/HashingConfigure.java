package com.bookstore.app.infrastructure;

import lombok.Data;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Data
public class HashingConfigure {
    private SecretKeyFactory factory;
    private byte[] salt;
    private int iterationsCount =  65536;
    private int keyLength = 128;
    public HashingConfigure() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        salt = new byte[16];
        secureRandom.nextBytes(salt);
        factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    }
    public byte[] Hash(String password) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterationsCount, keyLength);
            return factory.generateSecret(spec).getEncoded();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
