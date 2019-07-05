package com.umpaytest.design.factory.abst;

/**
 * @author: Hucl
 * @date: 2019/7/5 11:29
 * @description:
 */
public class GreenColor implements Color {
    @Override
    public void fill() {
        System.out.println("green color");
    }
}
