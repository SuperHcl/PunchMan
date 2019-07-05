package com.umpaytest.design.factory.abst;

/**
 * @author: Hucl
 * @date: 2019/7/5 11:37
 * @description:
 */
public class FactoryProducer {

    AbstractFactory getFactory(String choice) {
        if ("shape".equalsIgnoreCase(choice)) {
            return new ShapeFactory();
        } else if ("color".equalsIgnoreCase(choice)) {
            return new ColorFactory();
        }
        return null;
    }
}
