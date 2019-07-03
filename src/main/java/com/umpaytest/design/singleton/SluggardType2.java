package com.umpaytest.design.singleton;

/**
 * @author: Hucl
 * @date: 2019/6/27 14:41
 * @description: 懒汉模式(双重校验锁[不推荐])单例
 */
public class SluggardType2 {
    private volatile static SluggardType2 instance = null;

    //volatile 关键字可以禁止指令重排 ：可以确保instance = new SluggardType2()对应的指令不会重排序
    //但是因为对volatile的操作都在Main Memory中，而Main Memory是被所有线程所共享的，这里的代价就是牺牲了性能，无法利用寄存器或Cache
    private SluggardType2(){}

    public static SluggardType2 getInstance() {
        if (instance == null) {
            synchronized (SluggardType2.class) {
                if (instance == null) {
                    instance = new SluggardType2();
                }
            }
        }
        return instance;
    }

    public void say() {
        System.out.println("懒汉模式(双重校验锁[不推荐])单例");
    }
}
