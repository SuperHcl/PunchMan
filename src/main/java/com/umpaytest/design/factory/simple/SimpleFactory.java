package com.umpaytest.design.factory.simple;

/**
 * @author: Hucl
 * @date: 2019/7/3 16:25
 * @description: 简单工厂模式
 */
public class SimpleFactory {

    static Product create(String s) {
        if ("A".equals(s)) {
            return new ConcreteProductA();
        } else {
            return new ConcreteProductB();
        }
    }
}
