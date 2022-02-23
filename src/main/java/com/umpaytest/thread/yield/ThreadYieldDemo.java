package com.umpaytest.thread.yield;

/**
 * @author Hu.ChangLiang
 * @date 2021/11/12 4:06 下午
 */
public class ThreadYieldDemo extends Thread {
    public ThreadYieldDemo(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(this.getName() + " run---" + i);
            if (i % 2 == 0) {
                Thread.yield();
            }

        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 8; i++) {
            ThreadYieldDemo demo = new ThreadYieldDemo("线程 " + i);
            demo.start();
        }

    }
}
