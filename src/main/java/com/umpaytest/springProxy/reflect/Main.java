package com.umpaytest.springProxy.reflect;


import net.sf.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: Hucl
 * @date: 2019/6/25 16:57
 * @description: java反射
 */
public class Main {

    public static void main(String[] args) {
        /*Class clazz = Class.forName("com.umpaytest.springProxy.reflect.ReflectDemo");
        for (Method m : clazz.getMethods()) {
            System.out.println(m);
        }

        Method method = clazz.getMethod("selectOne", String.class);

        Object obj = clazz.getConstructor().newInstance();
        Object result = method.invoke(obj, "111");
        System.out.println(result);


        ReflectDemo aClass = ReflectDemo.class.newInstance();
        String newInstance = aClass.selectOne("newInstance");
        System.out.println(newInstance);*/

        ReflectDemo defaultInstance = getDefaultInstance(ReflectDemo.class);
        System.out.println(defaultInstance.selectOne("234"));

//        ReflectDemo par = getInstanceOfPar(ReflectDemo.class, "par");
//        System.out.println(par.selectOne("nnskw"));
        String jsonParams = "{}";
        JSONObject paramMap = JSONObject.fromObject(jsonParams);
        System.out.println(paramMap.get("code"));
        System.out.println(paramMap.getString("code"));


    }

    public static <T> T getDefaultInstance(Class<T> clazz) {
        try {
            return (T) clazz.getConstructor(String.class).newInstance("123");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("getDefaultInstance occur an error!");
        }

    }


    public static <T> T getInstanceOfPar(Class<T> clazz, Object... args) {
        Constructor<?>[] constructors = clazz.getConstructors();

        try {
            return (T) clazz.getDeclaredConstructor(constructors[0].getParameterTypes()).newInstance(args);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("getInstanceOfPar occur an error!");
        }
    }


}
