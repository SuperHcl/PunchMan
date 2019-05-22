package com.umpaytest.test;


import java.util.ArrayList;
import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/3/22 16:21
 * @description: ThreadLocal test
 * ThreadLocal用于保存某个线程共享变量：对于同一个static ThreadLocal，
 * 不同线程只能从中get，set，remove自己的变量，而不会影响其他线程的变量。
 */
public class ThreadLocalTest {

    private static final ThreadLocal<Object> threadLocal = new ThreadLocal<Object>() {

        /**
         * ThreadLocal没有被当前线程赋值时或当前线程刚调用remove方法后调用get方法，返回此方法值
         */
        @Override
        protected Object initialValue() {
            System.out.println("调用get方法时，当前线程共享变量没有设置，调用initialValue获取默认值!");
            return null;
        }
    };

    private static List<Object> threadList = new ArrayList<>();

    public static class MyIntegerTask implements Runnable {

        private String name;

        MyIntegerTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                if (ThreadLocalTest.threadLocal.get() == null) {
                    ThreadLocalTest.threadLocal.set(0);
//                    ThreadLocalTest.threadList.add("MyIntegerTask--" + ThreadLocalTest.threadLocal.get());
                    System.out.println("线程" + name + ": " + ThreadLocalTest.threadLocal.get());
                } else {
                    int num = (int) ThreadLocalTest.threadLocal.get();
//                    ThreadLocalTest.threadList.add(num);
                    ThreadLocalTest.threadLocal.set(num + 1);
                    System.out.println("线程" + name + ": " + ThreadLocalTest.threadLocal.get());
                    if (i == 3) {
                        ThreadLocalTest.threadLocal.remove();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static class MyStringTask implements Runnable {
        private String name;

        MyStringTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                if (null == ThreadLocalTest.threadLocal.get()) {
                    ThreadLocalTest.threadLocal.set("a");
//                    ThreadLocalTest.threadList.add("MyStringTask--" + ThreadLocalTest.threadLocal.get());
                    System.out.println("线程" + name + ": a");
                } else {
                    String str = (String) ThreadLocalTest.threadLocal.get();
//                    ThreadLocalTest.threadList.add(str);
                    ThreadLocalTest.threadLocal.set(str + "a");
                    System.out.println("线程" + name + ": " + ThreadLocalTest.threadLocal.get());
                }
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static void printList() {
        ThreadLocalTest.threadList.forEach(s -> {
            System.out.print(s);
            System.out.println();
        });
    }

    public static void main(String[] args) {
        new Thread(new MyIntegerTask("IntegerTask__1")).start();
        new Thread(new MyStringTask("StringTask__1")).start();
//        new Thread(new MyIntegerTask("IntegerTask__2")).start();
//        new Thread(new MyStringTask("StringTask__2")).start();


//        String url = "/umpaydc/dataQuery/dpsService";
//        String str = "www.yiibai.com";
//
//        System.out.println(url);
//        System.out.println(str.startsWith("www"));
//
//        System.out.println(url.startsWith("/umpaydc/dataQuery/"));
    }


}
