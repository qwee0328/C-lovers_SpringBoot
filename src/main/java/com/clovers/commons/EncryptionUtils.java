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
    
    /* **********************************************
	 * 자음 모음 분리
	 * 바보 -> ㅂㅏㅂㅗ
	 * **********************************************/
	/** 초성 - 가(ㄱ), 날(ㄴ) 닭(ㄷ) */
	public static char[] arrInitialConsonant = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138,
			0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148,
			0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
	/** 중성 - 가(ㅏ), 야(ㅑ), 뺨(ㅑ)*/
	public static char[] arrMiddleConsonant = { 0x314f, 0x3150, 0x3151, 0x3152,
			0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a,
			0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162,
			0x3163 };
	/** 종성 - 가(없음), 갈(ㄹ) 천(ㄴ) */
	public static char[] arrFinalConsonant = { 0x0000, 0x3131, 0x3132, 0x3133,
			0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c,
			0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145,
			0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
	
	/* **********************************************
	 * 알파벳으로 변환
	 * 멍충 -> ajdcnd 
	 * **********************************************/
	/** 초성 - 가(ㄱ), 날(ㄴ) 닭(ㄷ) */
	public static String[] arrInitialConsonantEng = { "r", "R", "s", "e", "E",
		"f", "a", "q", "Q", "t", "T", "d", "w",
		"W", "c", "z", "x", "v", "g" };
 
	/** 중성 - 가(ㅏ), 야(ㅑ), 뺨(ㅑ)*/
	public static String[] arrMiddleConsonantEng = { "k", "o", "i", "O",
		"j", "p", "u", "P", "h", "hk", "ho", "hl",
		"y", "n", "nj", "np", "nl", "b", "m", "ml",
		"l" };
 
	/** 종성 - 가(없음), 갈(ㄹ) 천(ㄴ) */
	public static String[] arrFinalConsonantEng = { "", "r", "R", "rt",
		"s", "sw", "sg", "e", "f", "fr", "fa", "fq",
		"ft", "fx", "fv", "fg", "a", "q", "qt", "t",
		"T", "d", "w", "c", "z", "x", "v", "g" };
 
	/** 단일 자음 - ㄱ,ㄴ,ㄷ,ㄹ... (ㄸ,ㅃ,ㅉ은 단일자음(초성)으로 쓰이지만 단일자음으론 안쓰임) */
	public static String[] arrSingleConsonantEng = { "r", "R", "rt",
		"s", "sw", "sg", "e","E" ,"f", "fr", "fa", "fq",
		"ft", "fx", "fv", "fg", "a", "q","Q", "qt", "t",
		"T", "d", "w", "W", "c", "z", "x", "v", "g" };
	
	public static String kR_EnKeyboardConversion(String input) {
		String word = input; // 분리할 단어
		//String result = ""; // 결과 저장할 변수
		String resultEng = ""; // 알파벳으로
		
		for(int i=0;i<word.length();i++) {
			// 한글자씩 읽어들인다.
			char chars = (char) (word.charAt(i) - 0xAC00);
			
			// 자음과 모음이 합쳐진 글자인 경우
			if(chars>=0&&chars<=11172) {
				// 초성, 중성, 종성 분리
				int initialConsonant = chars/(21*28); // 초성
				int middleConsonant = chars%(21*28)/28; // 중성
				int finalConsonant = chars%(21*28)%28; // 종성
				
				// 알파벳으로 변경
				resultEng = resultEng+arrInitialConsonantEng[initialConsonant]+arrMiddleConsonantEng[middleConsonant];
				// 자음 분리 - 종성이 존재할 경우
				if(finalConsonant!=0x0000) {
					resultEng=resultEng+arrFinalConsonantEng[finalConsonant];
				}
			}else { // 한글이 아니거나 자음만 있는 경우
				// 알파벳으로 변경
				if(chars>=34097 && chars<=34126) {
					// 단자음인 경우
					int singleConsonant = (chars-34097);
					resultEng=resultEng+arrSingleConsonantEng[singleConsonant];
				}else if(chars>=34127 && chars<=34147) {
					// 단모음인 경우
					int singleVowel = (chars-34127);
					resultEng = resultEng+arrMiddleConsonantEng[singleVowel];
				}else {
					// 알파벳인 경우
					resultEng = resultEng + ((char)(chars+0xAC00));
				}
			}
		}
		return resultEng;
	}

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
