package com.umpaytest.design.singleton;

/**
 * @author: Hucl
 * @date: 2019/6/27 14:38
 * @description: 单例模式--懒汉
 */
public class SluggardType {

    private static SluggardType instance = null;
    private SluggardType(){}

    public static SluggardType getInstance() {
        if (instance == null) {
            return new SluggardType();
        }
        return instance;
    }

    public void say() {
        System.out.println("懒汉模式");
    }
}
