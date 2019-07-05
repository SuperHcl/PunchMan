package com.umpaytest.design.factory.method;


/**
 * @author: Hucl
 * @date: 2019/7/3 16:36
 * @description: 工厂方法模式
 */
public class MethodFactoryClient {

    public static void main(String[] args) {

        AnimalFactory catFactory = new CatFactory();
        Cat cat = (Cat) catFactory.create();
        cat.sleep();

        Dog dog = (Dog) new DogFactory().create();
        dog.sleep();
    }
}
