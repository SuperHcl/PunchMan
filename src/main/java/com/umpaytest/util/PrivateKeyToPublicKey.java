package com.umpaytest.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.math.BigInteger;

public class PrivateKeyToPublicKey {
    public static void main(String[] args) {
        // 示例私钥字符串，你需要替换为实际的私钥字符串
//        String privateKeyString = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDYTcY+L/LxKCzKRjWVM8iT+PHuY6RLgHh0+bblzGUFum64w3tIgah+YFfF+BZ49EeYK9O9nPfTHx0IdPygIk5lyfWBQevdl+Ma3uTmWvi4UKJsaUOerhAnRrLGZaElnMBnXhiLGpTBZfKDjqWMCit9nGZASecsOk2R0zQUPBXNmaOHfuoUdL/8kdEkQKY1Ksv2t3aDEmfb+cyAd3BpBC2vM+HnhBl81lWd8aWSQC7fDMUFDtUt5AnLVfqnnxpFPgWTvvYyhjl2I9xhtWAKsuw5vAJFrvW45ChNmE7QEJLsj1KoSMrmwUEiRKkP/xiwx9aeb1K1l4YMehz45Z/6DKl3AgMBAAECggEAReJq6HCpd0zZm8ivSzNd3KET2UeML3CkenarpvcwtHcVitBtpnFttNb/xNBwwkd6Uinm0V5Dm4xz3uWdMUekOLLVhRt9TyzhjS7ymZJOxY4XzBr+Y+gtA+ONoxf2xqYsa7k1CMxfv1Rm1CZkSiiHAKBlUOQPsnRr1297ZgY7rR8cbN6nSvurf6ZhBe2OplSIRoS0QPwrPjC/Vl5LwC7hWYxqymrUYBtEryMishOwe2fdEYKrJOFVxucXv10QxqIQEGQb5zA4BcsLDFZDdsIXbygBHeDdfEjLhyzwoLnU4asdMeFUtkUTa08/L7duhuP74zcI3w14K8Yp8vnEPm16sQKBgQDtLgv2dj39hRB1ssPylAzqmeIRZShiZT3Pjbjta5secO2aLXQ1k8xJYGxufjCYkVbSD7AxbBP9M8W43qG6oPNLR1VEXLOCP1s6fnO5VMgGE0Qv4TyFPPBRozNRxOhpVVWShpTWozT2YJUUXe0jtHIvk9whNKmasZQTE9DlaWJZ1QKBgQDpd6lnhSCB9hFqxSUc0PriOG4P3B0Wh+1GGu/QgdDS+uLSP1Nw9/aGRl9mEh8tRiNWG0OMq4lcgYxz5+5bE0VNh1bB+OItNxXOgifN97G1pzAr4t0JAGLgzDF8dIfG/QB0zAyFQWw1R5DXX8B9VhAG84UL8zoY/jXWhX+dz+BwGwKBgQDFTyE0j3lShngC8gyAqRl1sT6YKgdGk+Kv376QBIyu7lM7h8S9LY8tG8IYyXGz+nS3m8V/1+FpKsC54ru4flznqxgMHvJW0E4cg0XST+124GBBVKb8UI273IC87YpK1mqx0rPPO0a8D+z1XI4CvgKgAemG1kCiKEh+9mU59EmVYQKBgQDfHJHx8hFc4XFHnduXrEb2BYN4+Jt7uq6P/pILquOLnGNDFW2CWhVnwLzOaKcOtyZx1WA6gHumwc8dvRnWQ5ZJPvs0J8rN9wSJ82xis0FLHdk1N3qQbaQQvFLWYu6WxZW4CIA2oIzuZdQPXtwvax2p5vLQtsJgvB9rQOzyPYR43QKBgQDJNQHRtcKWucnK/goBD1matDNzR7EVcGLAG9zQ+lHM2dZrvgH77B79veBVNQ6q3Q/ndlfQXx7luS9FGq0IXYtVikqJI3u6oRdO7pILFPF68rl/5ydOIhrZb7xUMsUAw7KKyuctpLq0qJAYD/L+4MJEjj5pWAH0e+3ugvtVp1HolA==";
//        String privateKeyString = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCMH45suRgeEFRJd145fqa4iAJO4PyhQtDjCvp/HB4jRpMYQWnwKiIufzu3vnOE5kGpQNYoWAQgfPG7/9eYOnwAd1pyDfeMdiOqUrcj2HoX9EjDg/dnYEM8e3v176fAZaekvMvuh07CNWFI3Hk3DYIj/VYQlLzpC2zi+uWUYhpCHWhJ3WPu1ODjLkDVAqRs+zPZf+3D8h+Le68VnQlGYWHZh5GvKRJ0nUjElctqrSvbhRyyPyod2nFRUaf+k17SnOd75mamkdCHPXJIcRRAV1/lRwf3XlWOtilr7uM26zs3lhWMFlK/ruOSNCTnDGGpSsm08S//szliviia7B/Rc64RAgMBAAECggEAR6oD/5sSuJ2FXiWMrT8XKZ12AL2Uty0jBFujDctlpoj7xhX+hnQmEz3DXxAqqLfSsSuV3ABX6/267HnG2/ZdYSepvFdkWhQ53Wx8efrHRuULKeOebuKOwKovFfNCCmZzPQ/KBO5y22TNoqQP4pYoDxwMHmLT0ySixdun+yHJaDI8UOV2s7rbQCQsdaTGH2EKQEmDXhhWTljSIPc30l7T4XYa+5lU+Wdg4qwwJJbjgmZLZZnpF2NZ56qKLv9YA0mUalNI9gc1C64juU+9wPHtTZFsTDKaKGrKy+waqhDF40rmJILLTsfpqlcOblQScJ98M3Rds6imptOlaxhBtwXbWQKBgQDB5/WOf/4uY3LBibFPTW/E1dJySarIAKCZHV5xPtb9jHDyuKRaLYpCuhNkbVYKRFcHHPEvAAqAg2+7Gnl9XsEpnA5hRfonwCZI+uo5WmUjQnchLQ+cQ/eL65DbRCqsNzsOTSy8ePbfC7XHSAInWG1kOAhNFbpc1XsEqXT3MUV52wKBgQC4/pY4cFbKAAjgsP8XeHv6UM4Ja19GgVC7rbiPzJaEvmqd6UW0XIx9vQ4W4S4R/At4zC3WFDsxxySG8ltMIYb3NB7QAqmw+Uqk1se0x+rTj3+dfJRcjCGXJDQ/mFtYJaMCm0aGFmyNzaAiOFMR9+LnkNrio4jgGYS7IhrQGFrpgwKBgQC9Ozabs3MGKE00bMCZ6LM7v4PZvuSovFv+MPgawj2jQOx1IxZZXvn/9oG4Ty9ZY6oeqfMBcCPPmPVv/f00CUWu6rnp2hWvt0hljmEISZxL1bBWsxf54yZw6Rn7scGHQXsjkNMot1ANCeuDKFXZueU/maMYPy0ZLckZGpqoJfBsYwKBgAx4jSPs3szrR63uAKRSaPks7VbRODEGkqy0P1biUdtNAt72VM6d1pK7ZfHA6FwqA5P3H7fo0Ty/0gSO3fy53eFYUCNxqoLVq2SFXh0fxQLSD6ZMOawrfjA0LpmC6dV9ZAnO8egkJtlAXWUxKthxP4igyUCyCY4h6kK7P9d7bNWFAoGACjSkusOCwgtHtJaTuxofJTMTw2wX+g2n/ydY3nckl6T2kDNeOIBsHkTTBNDgKCHRvTKBURK6yElWE8GWJP6dVYGmDzsxu9MzR5aTSoBgGeyUimJc73elwctzhpqebtVLiegpDAJ4FXAD4djaJ282lNbrODMbsp80laM8V0ss9yg=";
        String privateKeyString = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCD6SWEF47hKdvIpxNyM8ahNaBkY6RLzTzR/V1oI0n1Vp+pkbP4Xqe3zBcAUzitPlt/KuxdJs5aS4KHJs5JCjWp8EjhG+E6hKE84T42tEV2Z3wT8sFCEbGsxmrnnOrx+atRh5DmD5qGLuUrtWiB1PZXDAm3rxsHCorJsjDLKfRtZmEJwGGooX9jDldq3f598V1OsEcW8ItnnXUwQO/ywn9rmQPmfxzUWP5Sezedo7855vVCNdgpdaycjJXMB4z0v1A7DfZ8hRfTocZ4/pv8uBji5f0MFebg024Hvsyk0cNuVEYcYm91GkwyI+gDE68t3DOmxKf7TRTIyKF7s1UzYvGRAgMBAAECggEAK+NtjQZ9jmLr8lXMfYnGg6qhA+W3K1VM8B0Zb+WV3OAGXVfSBkgQC3IHER+fUtzVenkgjVm0/Iv1ENyTc+/GrRT6WpN5G4w3TVcv6OmP1vZVbRmuvTm/4NlfhlJKRVopawXs6u60HgMKUsGX1OKzV4D01r2XA3+5b6Qu4dnCrocJsFVr9EVO1cFgDV9N6tqXyskOtF9cDpA3Ga9OZXddf0g4uW0BKImIwCZeBXIY75vcx2ZRS3cf7s9szb7sJ4I3+0A/GzWtBh60J3RYI9qXMF2QpXBcqRLv88WwkofmaWU7/GcIRt+v/xLLafZtlN/S1/LXnmqnWR6iii8BYkuSJQKBgQD/mG2H5KgwwVXu9KN+tIeMBwvp04ojqnp+NG46sRBTt2CjV6ZFBszdwYb+1cmhybWg2UD9piIbpMAnuwBYzd1RWFwvP/nbMe+6DdbcChPAJQnGTVvP5k3dRZDmRMTflARyYGji28/ws0TkbCkb1xsbND8msiIcXfMvgGbxoSHEywKBgQCEHplrQanty8Kl4QPnHMawPOO7vsSF76QE/b5laaPEAUOi3D48+b3wPxApeS2SvYR7jKtxu3PWBqJeWBWcQswSDVhA2agmmOS0iiTm0WtzuL6IrnFeTu2q4mYz9A5twJq8/1FTDF7bkyA28esP770wcox13v74U3h0DNIJCY+zkwKBgETvy7r3RRXBT3gfq2klL9nonKH4WC2+cUNQyNozn3+02e+0WDgN2XuGEu7wom1shfxYHFtNT629BcaxfSp2e9bQyixESROBqHK6ANVgMmuEkdpWSwkLyLyBYs94hND4jyp6Lk/hkXgOIeqp8Xx8y+bOdKnvKZwVCt4o95r8Jqt5AoGBAIHpfL4kkS0MYNevTKEigJSnKt9fk+qHW2a1A1TA7ZE8FP+9RU273PKxb/j3+MIDrJO4HJT2184qM9pFDuraG8Pcb11FKLzbNnf4JpT4c4oUrPXFYiBzx+tyN0D6yH1PGFijTZo13chXASRF+Pdwqz1KRadZoKkZmqWyPY05Y54VAoGBAKrGtGmWp1rMBiQCSmlm+Gj85ANmsvA6JoW0EDAWiTjyi9txjrHx0ARIOLZ1p6FHejlf/dPj5+zxNeZRK+7lZON7USSTB71Grl3uvr9wbgJevqqns1OaW0I5sA4K26E/5FIEgMbOUetwDumMMAAA0a4g4CjCvgewlmOkL/O7Wybn";
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
