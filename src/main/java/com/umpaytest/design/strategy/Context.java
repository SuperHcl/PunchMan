package com.umpaytest.design.strategy;

/**
 * @author: Hucl
 * @date: 2019/7/22 09:37
 * @description: 封装类。把所有的策略封装隐藏起来，对外暴露本类 供 具体策略的使用。
 */
public class Context {
    private IStrategy strategy;

    public Context(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void operate() {
        strategy.operate();
    }
}
