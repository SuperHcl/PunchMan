package com.umpaytest.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Random;

/**
 * 非对称加密算法 RAS的公钥、私钥生成器
 * @author Hu.ChangLiang
 * @date 2025/4/11 10:09
 */
public class RSAKeyPairGenerator {
    private static final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();

    public static void main(String[] args) throws Exception {
        // 生成密钥对
        KeyPair keyPair = generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // 打印私钥
        byte[] privateKeyBytes = privateKey.getEncoded();
        String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKeyBytes);
        System.out.println("Private Key (Base64): " + privateKeyBase64);

        // 打印公钥
        byte[] publicKeyBytes = publicKey.getEncoded();
        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKeyBytes);
        System.out.println("Public Key (Base64): " + publicKeyBase64);

        // 生成随机字符串
        String randomString = generateRandomString(32);
        System.out.println("Random String: " + randomString);
        System.out.println("a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6".length());
    }

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHA_NUMERIC.length());
            sb.append(ALPHA_NUMERIC.charAt(index));
        }
        return sb.toString();
    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // 密钥长度，可根据需求调整，如 1024、2048、4096 等
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}
