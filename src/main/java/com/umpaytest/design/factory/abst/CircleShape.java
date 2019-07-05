package com.umpaytest.design.factory.abst;

/**
 * @author: Hucl
 * @date: 2019/7/5 11:27
 * @description:
 */
public class CircleShape implements Shape {
    @Override
    public void draw() {
        System.out.println("circle shape");
    }
}
