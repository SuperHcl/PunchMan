package com.umpaytest.springProxy.reflect;

import java.lang.reflect.Method;

/**
 * @author: Hucl
 * @date: 2019/6/25 16:57
 * @description: java反射
 */
public class Main {

    public static void main(String[] args) throws Exception{
        Class clazz = Class.forName("com.umpaytest.springProxy.reflect.ReflectDemo");
        for (Method m : clazz.getMethods()) {
            System.out.println(m);
        }

        Method method = clazz.getMethod("selectOne", String.class);

        Object obj = clazz.getConstructor().newInstance();
        Object result = method.invoke(obj, "111");
        System.out.println(result);

    }


}
