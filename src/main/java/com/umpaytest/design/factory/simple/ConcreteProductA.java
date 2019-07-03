package com.umpaytest.design.factory.simple;

/**
 * @author: Hucl
 * @date: 2019/7/3 16:27
 * @description:
 */
public class ConcreteProductA implements Product {
    @Override
    public void operation1() {
        System.out.println("product A operation 1");
    }

    @Override
    public void operation2() {
        System.out.println("product A operation 2");
    }
}
