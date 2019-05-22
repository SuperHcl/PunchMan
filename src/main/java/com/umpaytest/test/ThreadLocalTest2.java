package com.umpaytest.test;

/**
 * @author: Hucl
 * @date: 2019/3/22 17:19
 * @description:
 */
public class ThreadLocalTest2 {

    private ThreadLocal<Long> longThreadLocal = new ThreadLocal<>();
    private ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

    public void set() {
        longThreadLocal.set(Thread.currentThread().getId());
        stringThreadLocal.set(Thread.currentThread().getName());
    }

    public long getLong() {
        return longThreadLocal.get();
    }

    public String getString() {
        return stringThreadLocal.get();
    }


    public static void main(String[] args) throws InterruptedException {
        ThreadLocalTest2 threadLocalTest2 = new ThreadLocalTest2();
        threadLocalTest2.set();


        System.out.println("线程--"+Thread.currentThread().getName()+"~~~~"+threadLocalTest2.getLong());
        System.out.println("线程--"+Thread.currentThread().getName()+"~~~~"+threadLocalTest2.getString());

        Thread thread = new Thread(){
            @Override
            public void run() {
                threadLocalTest2.set();
                System.out.println("线程--"+Thread.currentThread().getName()+"~~~"+threadLocalTest2.getLong());
                System.out.println("线程--"+Thread.currentThread().getName()+"~~~"+threadLocalTest2.getString());
            }
        };
        thread.start();
        thread.join();

        System.out.println("线程--"+Thread.currentThread().getName()+"~~~~"+threadLocalTest2.getLong());
        System.out.println("线程--"+Thread.currentThread().getName()+"~~~~"+threadLocalTest2.getString());
    }
}
