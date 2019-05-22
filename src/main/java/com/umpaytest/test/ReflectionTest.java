package com.umpaytest.test;


import com.umpaytest.entity.Test;

import java.lang.reflect.Method;

/**
 * @author: Hucl
 * @date: 2019/3/27 18:18
 * @description: 针对单例模式的反射
 */
public class ReflectionTest {

    public static void main(String[] args) {
        try {
            Class<?> test = Class.forName("com.umpaytest.entity.Test");
//            Method[] methods = test.getMethods();
//
//            for (Method method : methods) {
//                System.out.println(method);
//            }



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
