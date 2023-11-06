package com.clovers.commons;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncryptionUtils {
	
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";  // AES/CBC 방식 암호화방식
    private static final byte[] KEY = "clover2ndproject".getBytes(); // 16 bytes for AES/CBC
    private static final byte[] IV = "1234567890123456".getBytes();  // 16 bytes for AES/CBC

    // SHA-256 해시 알고리즘
	public static String getSHA256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(input.getBytes("UTF-8"));
			return String.format("%064x", new java.math.BigInteger(1, digest.digest()));
		} catch (Exception e) {
			log.error("SHA-256 hashing error", e);
			throw new RuntimeException(e);
		}
	}
	
	// SHA-512 해시 알고리즘
	public static String getSHA512(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(input.getBytes("UTF-8"));
			return String.format("%0128x", new java.math.BigInteger(1, digest.digest()));
		} catch (Exception e) {
			log.error("SHA-512 hashing error", e);
			throw new RuntimeException(e);
		}
	}
	
	// AES 암호화 알고리즘 -> 평문 -> Base64 변환
    public static String encryptAES(String plainText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new javax.crypto.spec.IvParameterSpec(IV));
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    
    // AES 복호화 알고리즘 -> Base64 -> 평문 변환
    public static String decryptAES(String encryptedText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new javax.crypto.spec.IvParameterSpec(IV));
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }
}
