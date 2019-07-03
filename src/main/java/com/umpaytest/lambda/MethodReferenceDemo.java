package com.umpaytest.lambda;

import java.util.Arrays;

/**
 * @author: Hucl
 * @date: 2019/6/26 19:38
 * @description: method reference demo
 * e.g 类名:: 静态方法名
 */
public class MethodReferenceDemo {

    static int name(String s1, String s2) {
        return s1.compareTo(s2);
    }

    static int nameIgnoreCase(String s1, String s2) {
        return s1.toLowerCase().compareTo(s2.toLowerCase());
    }

    static int length(String s1, String s2) {
        int n1 = s1.length();
        int n2 = s2.length();
        if (n1 == n2) {
            return s1.compareTo(s2);
        }
        return n1 < n2 ? -1 : 1;
    }

    static int add(int a, int b) {
        return a+b;
    }

    public static void main(String[] args) {
        String[] array = "Java apple lambda functional OOP".split(" ");
//        Arrays.sort(array, MethodReferenceDemo :: length);
        Arrays.sort(array, String :: compareToIgnoreCase);
        System.out.println(Arrays.toString(array));



    }
}
