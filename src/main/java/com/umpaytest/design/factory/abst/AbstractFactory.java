package com.umpaytest.design.factory.abst;

/**
 * @author: Hucl
 * @date: 2019/7/5 11:31
 * @description:
 */
public abstract class AbstractFactory {
    protected abstract Shape getShape(String shape);

    protected abstract Color getColor(String color);
}
