package com.umpaytest.design.adapter;

/**
 * @author: Hucl
 * @date: 2019/7/22 14:56
 * @description: 适配器角色
 */
public class Adapter extends Adaptee implements Target {
    @Override
    public void request() {
        super.doSomething();
    }
}
