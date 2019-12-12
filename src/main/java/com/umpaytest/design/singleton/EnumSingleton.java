package com.umpaytest.design.singleton;


/**
 * @author: Hucl
 * @date: 2019/6/27 15:11
 * @description: 单例模式--枚举实现
 */
public enum EnumSingleton {

    INSTANCE;

    public void say() {
        System.out.println("枚举单例");
    }
}
