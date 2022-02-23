package com.umpaytest.thread.join;

/**
 * @author Hu.ChangLiang
 * @date 2021/11/12 3:24 下午
 */
public class ThreadJoinDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main thread start...");
        Thread thread = new Thread(new JoinThread());
        thread.setName("JoinThread ");
        thread.start();
        // thread.join的作用是等thread这个线程执行完，再执行其他的线程。
        // 通俗点讲就是让主线程等待其他线程执行完后，主线程再继续执行
        thread.join();
        System.out.println("main thread end...");
    }
}

class JoinThread implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + i + " running...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " ending...");
    }
}
