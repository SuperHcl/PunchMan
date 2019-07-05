package com.umpaytest.design.factory.abst;

/**
 * @author: Hucl
 * @date: 2019/7/5 11:36
 * @description:
 */
public class ColorFactory extends AbstractFactory {
    @Override
    protected Shape getShape(String shape) {
        return null;
    }

    @Override
    protected Color getColor(String color) {
        if ("red".equals(color)) {
            return new RedColor();
        } else if ("green".equals(color)) {
            return new GreenColor();
        } else if ("yellow".equals(color)) {
            return new YellowColor();
        } else {
            return null;
        }
    }
}
