package com.umpaytest.design.singleton.reflect;

import com.umpaytest.design.singleton.HungryType;
import com.umpaytest.design.singleton.SluggardType;

import java.lang.reflect.Constructor;

/**
 * @author: Hucl
 * @date: 2019/7/12 11:39
 * @description: 通过反射得到 懒汉和饿汉模式的单例
 */
public class ReflectHungryAndSluggardSingleton {
    public static void main(String[] args) throws Exception {
        HungryType hungryType = (HungryType) getHungrySingleton();
        hungryType.say();

        SluggardType sluggardType = (SluggardType) getSluggardSingleton();
        sluggardType.say();
    }



    // 饿汉
    private static Object getHungrySingleton() throws Exception {
        Constructor<HungryType> hungryTypeConstructor = HungryType.class.getDeclaredConstructor();
        hungryTypeConstructor.setAccessible(true);
        return hungryTypeConstructor.newInstance();
    }

    // 懒汉
    private static Object getSluggardSingleton() throws Exception {
        Class<?> sluggardTypeConstructor = Class.forName("com.umpaytest.design.singleton.SluggardType");
        Constructor<?> declaredConstructor = sluggardTypeConstructor.getDeclaredConstructor();
        // 暴力破解private访问符的构造方法
        declaredConstructor.setAccessible(true);
        return declaredConstructor.newInstance();
    }
}
