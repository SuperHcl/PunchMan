package com.umpaytest.design.adapter;

/**
 * @author: Hucl
 * @date: 2019/7/22 14:52
 * @description:
 */
public class ConcreteTarget implements Target {
    @Override
    public void request() {
        System.out.println("If you need any help, please call me!");
    }
}
