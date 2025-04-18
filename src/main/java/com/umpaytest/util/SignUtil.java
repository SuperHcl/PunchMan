package com.umpaytest.util;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Hu.ChangLiang
 * @date 2025/4/8 11:41
 */
public class SignUtil {

    private String value = "appId=183f92c4dd25446993718bc204c7a54d&requestBody={\"code\":\"000861\"}&requestId=1742894795593&t=1742894795593";

    public static String sign(String value) {
        try {
            String privateKey = "XXXXX";
            String algorithm = "SHA256withRSA";
            Signature privateSignature = Signature.getInstance(algorithm);
            privateSignature.initSign(getPrivateKey(privateKey), new SecureRandom());
            privateSignature.update(value.getBytes(StandardCharsets.UTF_8));

            byte[] signature = privateSignature.sign();
            return Base64.getUrlEncoder().encodeToString(signature);
        } catch (Exception e) {
            throw new RuntimeException("Error signing data: " + e.getMessage(), e);
        }
    }

    private static PrivateKey getPrivateKey(String privateKeyStr) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
