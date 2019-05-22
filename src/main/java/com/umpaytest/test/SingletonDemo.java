package com.umpaytest.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: Hucl
 * @date: 2019/3/27 19:01
 * @description: 单例模式
 */
public class SingletonDemo {
    private static SingletonDemo singleton;
    private static int count;
    private SingletonDemo(){
        synchronized (SingletonDemo.class) {
            if (count>0) {
                throw new RuntimeException("error:创建了两个实例!");
            }
            ++count;
        }
    }

    public static SingletonDemo getInstance() {
        if (singleton == null) {
            singleton = new SingletonDemo();
        }
        return singleton;
    }
}

class SingletonReflectionTest {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        SingletonDemo s1 = SingletonDemo.getInstance();

        Constructor<SingletonDemo> constructor = SingletonDemo.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SingletonDemo s2 = constructor.newInstance();

        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());

    }
}
