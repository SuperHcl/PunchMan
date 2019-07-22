package com.umpaytest.design.strategy;

/**
 * @author: Hucl
 * @date: 2019/7/22 09:44
 * @description: 执行者
 */
public class Client {
    public static void main(String[] args) {
        Context context;

        System.out.println("--刚刚到吴国的时候拆第一个锦囊--");
        context = new Context(new BackDoor());  // 拿到妙计
        context.operate();          // 拆开执行
        System.out.println("\n\n\n\n\n\n");

        System.out.println("--刘备乐不思蜀了，拆第二个锦囊--");
        context = new Context(new GlvenGreenLight());
        context.operate();      // 执行第二个锦囊
        System.out.println("\n\n\n\n\n\n");

        System.out.println("--孙权的小兵追了怎么办? 拆第三个锦囊--");
        context = new Context(new BlockEnemy());
        context.operate();      // 孙夫人退兵
        System.out.println("\n\n\n\n\n\n");
    }

}
