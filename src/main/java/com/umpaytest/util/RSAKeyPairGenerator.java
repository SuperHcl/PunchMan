package com.umpaytest.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * 非对称加密算法 RAS的公钥、私钥生成器
 * @author Hu.ChangLiang
 * @date 2025/4/11 10:09
 */
public class RSAKeyPairGenerator {
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
    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // 密钥长度，可根据需求调整，如 1024、2048、4096 等
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}
