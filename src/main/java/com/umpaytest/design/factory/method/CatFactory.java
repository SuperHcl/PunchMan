package com.umpaytest.design.factory.method;

/**
 * @author: Hucl
 * @date: 2019/7/4 19:22
 * @description:
 */
public class CatFactory implements AnimalFactory {
    @Override
    public Animal create() {
        return new Cat();
    }
}
