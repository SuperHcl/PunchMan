package com.umpaytest.util;

import java.math.BigInteger;

/**
 * @className: Base62Converter
 * @author: zhangjinfeng
 * @date: 2023/5/19 11:23
 **/
public class Base62Converter {
    private static final String BASE62_CHARS = "9KqZ2lO8M7Dh1g6WpYvRcFtGJnUyBb3TdNeX5PQwHaEzSx0L4jiVrCkAfmuIosX";
    private static final int BASE62 = 62;

    public static String toBase62(long decimal) {
        StringBuilder sb = new StringBuilder();
        while (decimal > 0) {
            sb.append(BASE62_CHARS.charAt((int) (decimal % BASE62)));
            decimal /= BASE62;
        }
        return sb.reverse().toString();
    }

    public static BigInteger toDecimal(String base62) {
        BigInteger decimal = BigInteger.ZERO;
        int length = base62.length();
        for (int i = 0; i < length; i++) {
            char c = base62.charAt(i);
            int charValue = BASE62_CHARS.indexOf(c);
            BigInteger power = BigInteger.valueOf(BASE62).pow(length - i - 1);
            BigInteger product = power.multiply(BigInteger.valueOf(charValue));
            decimal = decimal.add(product);
        }
        return decimal;
    }
}
