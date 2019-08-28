package com.umpaytest.design.adapter;

/**
 * @author: Hucl
 * @date: 2019/7/22 14:57
 * @description:
 */
public class Client {

    public static void main(String[] args) {
        // 原有的业务逻辑
        Target target = new ConcreteTarget();
        target.request();

        // 现在增加了适配器角色后的业务逻辑
        Target target1 = new Adapter();
        target1.request();
    }
}
