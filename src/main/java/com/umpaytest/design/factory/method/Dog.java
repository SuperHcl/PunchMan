package com.umpaytest.design.factory.method;

/**
 * @author: Hucl
 * @date: 2019/7/4 19:21
 * @description:
 */
public class Dog  extends Animal {
    @Override
    protected void sleep() {
        System.out.println("dog sleep");
    }
}
