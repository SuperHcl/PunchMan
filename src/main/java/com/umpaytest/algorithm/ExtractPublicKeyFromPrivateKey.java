package com.umpaytest.algorithm;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.math.BigInteger;
/**
 * RSA 公钥提取
 * @author Hu.ChangLiang
 * @date 2025/4/8 10:34
 */
public class ExtractPublicKeyFromPrivateKey {
    public static void main(String[] args) {
        // 示例私钥字符串，你需要替换为实际的私钥字符串
        String privateKeyString = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCV35kEnHASgO+lQvz3X+ucxZ+PMnw0FNzZ5AX1z/kMmWc/2KA+qRKopo8QDNxmiaOYEDI1r+lVRroszAGx9uyCcMhbptc5BIoaxkh9wgaQEXFciYi+PoNE4bdkqeshthld19ZqaB7W6S+56BWztf91h9uUU30GpqvFj/H6zqhfbg0Mq4FIf8aLZBI4IhWeRt2T65R6qKoVsSGaoyiwyiBojq4IVAUODkyJ3rhb4lrBtjpcjC3sAo5CAmsU/VC28G9/DXEt89ad/iIeSHF8t3bGt6r5yn7olvNMttLwWH76znrgIgCh940lDTG+feMj2Pv8PUg8P0zTG9/Wy7ITMOcpAgMBAAECggEARGW/+qe+SeOH+r7gnbCr4WcbU4mxvz8KL3Jt+enI+iQER9IAB7i3aDpjLesAptcHzv0NPQ0kCqP1kT4lQ+extjHsbk9tB3t335JAQQxMXHFpb9bgjhoPZF8yiAE36rGlT/uRwgv0jxXqjwhI5zQW+rde0YlslJ155Pa1c0Xe93WVfhmg5Z/4OlDIyZf8GQBM/UWr364vMTntCBqeP7BAi7FpUe7yFG1hqMqWx5Lbu1ZAbsnBn7sHHqbqxcVBxpiKJJulR9V42Rkfeubd6cbqyDClfSOIVXpLsUIitJNPFXVvmwmpwma1OeYdpWaZTa0qRONtFmBeUzSmKqPBqueHgQKBgQD2eeV/JIAZZLnOHCSKarh0xy+Gq0erJ3FH6r5Sxq44O7yAOmJjGgXzR5u8pECJEgm0NdSNvbvopZPtSqHRKWXPKSIcN9biYMkK4pGBKZcRRpt4E98OykFGniV5k9woaIMfXT/sKdnmGGXXUKReoXbzxfDMsf2qXF3UQQ/SnS2TmQKBgQCbqh9IJ0uSqoeOpiXgt6pl+pTByIxOlHhOJQW1dqyOmj5RJJH7fSYKRay4TYrv/jbTva0NK7Gsc+LKnOGx3Z6dUBv730MLr8p60gnfEnz6RMYAOprs2C+TXgjqCHEcfXTi+aZNtpYNAKjCx5VPpQ15qkVycXb6jRuntDkGOGsqEQKBgG6NAB3F7lr1bc1/m/glo/Rk/vElU/D1sP6z0IfqK2x3hrXw+f4/Rtn2jWjQN04DtCyLVHn61xSjZ7UwG2Jocgy4A1+qwAwroyDpvJ+1WtfZ1pPGPe8xUpPtDRv58O6C6gUgWzJ/Fwf4e31W41K6k1MFvlrhnshRw5gXUpSCAVZJAoGALo5+FqVyC+am7now3/nuo9uDPEYP+LydErot7+0ThP8K3lfytWXjx7S5CNJgWJEOstuK82J7HFn4naK4OetPsjswc2997PPhukQxoZ808+E32v+sMGQkLo40WlK2CdBX8j66h4mxbSgiUUdCF/UEh7vuU2EuNZeYitrzJzIekxECgYEAnMTrKR0XrwVp8lNFr+++J+YDhUd6759kz/NBHSPcyLffmbL0cdbWsDSZ1ZVxrT55ZVEg+jQvE7owMDqJN3FvkKgWrsVMGNzb/2TCxwbhsIYVySXn8s4ewGLXUOavKMyUpD6pLq8VTuz/H0YC5QHDmAC3IRJ9lxiFKA2jIr73pQg=";

        try {
            // 从私钥字符串生成 PrivateKey 对象
            PrivateKey privateKey = getPrivateKeyFromString(privateKeyString);

            // 从私钥生成公钥
            PublicKey publicKey = getPublicKeyFromPrivateKey(privateKey);

            // 将公钥转换为字符串
            String publicKeyString = getPublicKeyString(publicKey);

            System.out.println("Generated Public Key:");
            System.out.println(publicKeyString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从私钥字符串生成 PrivateKey 对象
     * @param privateKeyString 私钥字符串
     * @return PrivateKey 对象
     * @throws Exception 异常
     */
    public static PrivateKey getPrivateKeyFromString(String privateKeyString) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 从私钥生成公钥
     * @param privateKey 私钥对象
     * @return 公钥对象
     * @throws Exception 异常
     */
    public static PublicKey getPublicKeyFromPrivateKey(PrivateKey privateKey) throws Exception {
        java.security.interfaces.RSAPrivateKey rsaPrivateKey = (java.security.interfaces.RSAPrivateKey) privateKey;
        java.math.BigInteger modulus = rsaPrivateKey.getModulus();
        java.math.BigInteger publicExponent = new BigInteger("65537");
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, publicExponent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(publicKeySpec);
    }

    /**
     * 将公钥转换为字符串
     * @param publicKey 公钥对象
     * @return 公钥字符串
     */
    public static String getPublicKeyString(PublicKey publicKey) {
        byte[] publicKeyBytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }
}
