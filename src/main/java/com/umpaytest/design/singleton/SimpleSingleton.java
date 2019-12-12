package com.umpaytest.design.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: Hucl
 * @date: 2019/12/12 10:13
 * @description:
 */
public class SimpleSingleton {
    private static final SimpleSingleton instance;
    private static int count;

    static {
        instance = new SimpleSingleton();
    }
    private SimpleSingleton() {

        synchronized (SimpleSingleton.class) {
//            if (count>0) {
//                throw new RuntimeException("不能创建两个单例模式");
//            }
//            System.out.println("count++前 value --->" + count);
//            ++count;
            if (instance != null) {
                throw new RuntimeException("不能创建两个单例模式");
            }
        }
    }

    public static SimpleSingleton getInstance() {
//        if (instance == null) {
//            instance = new SimpleSingleton();
//        }
        return instance;
    }

    public void say() {
        System.out.println("simple singleton test");
    }

}

class SimpleSingletonClient {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Constructor<SimpleSingleton> declaredConstructor = SimpleSingleton.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        Field countField = SimpleSingleton.class.getDeclaredField("count");
        countField.setAccessible(true);

        SimpleSingleton instance = SimpleSingleton.getInstance();
        System.out.println(instance);
        countField.set(countField, 0);

        SimpleSingleton simpleSingleton = declaredConstructor.newInstance();
        simpleSingleton.say();
        System.out.println(instance == simpleSingleton);

    }
}
