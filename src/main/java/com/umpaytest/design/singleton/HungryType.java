package com.umpaytest.design.singleton;

/**
 * @author: Hucl
 * @date: 2019/6/27 14:31
 * @description: 单例模式之饿汉模式
 */
public class HungryType {
    private static final HungryType instance = new HungryType();

    private HungryType(){}

    public static HungryType getInstance() {
        return instance;
    }
    public void say() {
        System.out.println("hungry type");
    }
}
