package com.umpaytest.thread.create;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hu.ChangLiang
 * @date 2022/2/22 12:57 下午
 */
public class ThreadCreateDemo {

    public static void main(String[] args) {
        // extends thread
        new Thread_01().start();
        // implements runnable
        new Thread(new Thread_02()).start();
        // thread pool线程池创建
        /*ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
            System.out.println("Thread pool -- run");
        });*/

        Object o = null;
        System.out.println((String) o);

        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        Map<String, String> map3 = new HashMap<>();

        map1.put("A", "1");
        map1.put("b", "1");
        map1.put("c", "1");

        map2.put("A", "2");
        map2.put("b", "1");
        map2.put("c", "1");

        map3.put("A", "3");
        map3.put("b", "1");
        map3.put("c", "1");

        list.add(map1);
        list.add(map2);
        list.add(map3);

        for (Map<String, String> ele : list) {


        }


    }

    static class Thread_01 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread 01 extends thread -- run");
        }
    }

    static class Thread_02 implements Runnable {

        @Override
        public void run() {
            System.out.println("Thread 02 implements runnable");
        }
    }
}
