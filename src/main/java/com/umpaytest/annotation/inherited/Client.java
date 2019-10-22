package com.umpaytest.annotation.inherited;

import java.util.Arrays;

/**
 * @author: Hucl
 * @date: 2019/9/19 10:55
 * @description:
 */
public class Client {

    public static void main(String[] args) {
        Class<Dog> dogClass = Dog.class;

        System.out.println("dog annotations : " + Arrays.toString(dogClass.getAnnotations()));

        System.out.println("哈士奇 annotations : " + Arrays.toString(Huskie.class.getAnnotations()));

        System.out.println("---------------------------------------");
        System.out.println("rose annotations : " + Arrays.toString(Rose.class.getAnnotations()));
        System.out.println("red rose annotations : " + Arrays.toString(RedRose.class.getAnnotations()));
    }
}
