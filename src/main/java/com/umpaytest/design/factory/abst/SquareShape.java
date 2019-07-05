package com.umpaytest.design.factory.abst;

/**
 * @author: Hucl
 * @date: 2019/7/5 11:28
 * @description:
 */
public class SquareShape implements Shape {
    @Override
    public void draw() {
        System.out.println("square shape");
    }
}
