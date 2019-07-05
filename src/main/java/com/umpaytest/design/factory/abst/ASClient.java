package com.umpaytest.design.factory.abst;

/**
 * @author: Hucl
 * @date: 2019/7/5 11:37
 * @description: 抽象工厂模式
 */
public class ASClient {

    public static void main(String[] args) {
        FactoryProducer factoryProducer = new FactoryProducer();

        ShapeFactory shapeFactory = (ShapeFactory) factoryProducer.getFactory("shape");
        shapeFactory.getShape("circle").draw();


        ColorFactory colorFactory = (ColorFactory) factoryProducer.getFactory("color");
        colorFactory.getColor("yellow").fill();
    }
}
