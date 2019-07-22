package com.umpaytest.design.strategy;

/**
 * @author: Hucl
 * @date: 2019/7/22 09:22
 * @description: 策略一 走后门
 */
public class BackDoor implements IStrategy {
    @Override
    public void operate() {
        System.out.println("策略一: 找乔国老走后门");
    }
}
