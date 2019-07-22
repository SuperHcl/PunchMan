package com.umpaytest.design.strategy;

/**
 * @author: Hucl
 * @date: 2019/7/22 09:24
 * @description: 策略二：吴国太开绿灯
 */
public class GlvenGreenLight implements IStrategy {
    @Override
    public void operate() {
        System.out.println("策略二：找吴国太开绿灯");
    }
}
