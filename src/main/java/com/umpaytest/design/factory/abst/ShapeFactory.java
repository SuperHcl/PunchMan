package com.umpaytest.design.factory.abst;

/**
 * @author: Hucl
 * @date: 2019/7/5 11:33
 * @description:
 */
public class ShapeFactory extends AbstractFactory {
    @Override
    protected Shape getShape(String shape) {
        if ("square".equals(shape)) {
            return new SquareShape();
        } else if ("circle".equals(shape)) {
            return new CircleShape();
        } else if ("rectangle".equals(shape)) {
            return new RectangleShape();
        } else {
            return null;
        }
    }

    @Override
    protected Color getColor(String color) {
        return null;
    }
}
