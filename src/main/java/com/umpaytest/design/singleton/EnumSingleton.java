package com.umpaytest.design.singleton;


/**
 * @author: Hucl
 * @date: 2019/6/27 15:11
 * @description: 单例模式--枚举实现
 */
public class EnumSingleton {

    private EnumSingleton() {
    }

    public static EnumSingleton getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private static enum Singleton {
        INSTANCE;

        private EnumSingleton singleton;

        //JVM会保证此方法绝对只调用一次
        private Singleton() {
            singleton = new EnumSingleton();
        }

        public EnumSingleton getInstance() {
            return singleton;
        }
    }

    public void say() {
        System.out.println("枚举单例");
    }
}
