package com.umpaytest.design.factory.simple;

/**
 * @author: Hucl
 * @date: 2019/7/3 16:24
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Product product = SimpleFactory.create("A");
        product.operation1();
    }
}
