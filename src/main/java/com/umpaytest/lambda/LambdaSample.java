package com.umpaytest.lambda;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: Hucl
 * @date: 2019/6/26 19:21
 * @description: lambda表达式联系
 */
public class LambdaSample {

    public void testOld01() {
        String[] words = "Improving code with Lambda expressions in Java".split(" ");

        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 忽略大小写排序
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        });

        System.out.println(Arrays.toString(words));
    }

    public void testNew01() {
        String[] words = "Improving code with Lambda expressions in Java".split(" ");
        Arrays.sort(words, (s1, s2) -> {
            return s1.toLowerCase().compareTo(s2.toLowerCase());
        });

        System.out.println("lambda --> "+Arrays.toString(words));
    }

    public static void main(String[] args) {
        LambdaSample l = new LambdaSample();
        l.testOld01();
        System.out.println();
        l.testNew01();
    }
}
