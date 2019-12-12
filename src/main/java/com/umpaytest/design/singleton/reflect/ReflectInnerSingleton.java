package com.umpaytest.design.singleton.reflect;

import com.umpaytest.design.singleton.InnerClassType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: Hucl
 * @date: 2019/7/12 11:34
 * @description: 反射破坏内部类的单例模式
 */
public class ReflectInnerSingleton {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName("com.umpaytest.design.singleton.InnerClassType");
        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        InnerClassType innerClassType = (InnerClassType) declaredConstructor.newInstance(null);
        innerClassType.say();
    }

}
