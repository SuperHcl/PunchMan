package com.umpaytest.test;

import com.umpaytest.guazi.Client;
import com.umpaytest.util.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Hucl
 * @date: 2019/4/25 14:52
 * @description:
 */
public class Test {
    private static final int TASK_NUM = 5;
    private static int num = 0;
    private static int flag = 0;
    private static Lock lock = new ReentrantLock();
    private static List<Condition> list = new ArrayList<>();
    private static ExecutorService exec = Executors.newCachedThreadPool();
    private static ArrayList<String> phoneLists = FileUtils.getPhoneList();

    private static Client client = new Client();


    static {
        for (int i = 0; i < TASK_NUM; i++) {
            list.add(lock.newCondition());
        }
    }

    private static void crit() {
        if (num >= phoneLists.size()) {
            System.exit(1);
        }
    }

    private static void print() {
        crit();
        System.out.print(Thread.currentThread());
//        for (int i = 0; i < 5; i++) {
            System.out.format("%-2s ", phoneLists.get(num++));

//        }
//        System.out.println();
        client.execute(phoneLists.get(num++), "32xa0m72p617f91uu7fw",
                "10001001", "Gcs6000051");
    }

    private static void work(int i) {
        while (!Thread.interrupted()) {
            try {
                lock.lock();
                if (flag == i) {
                    crit();
                    print();
                    flag = (i + 1) % list.size();
                    list.get((i + 1) % list.size()).signal();
                } else {
                    try {
                        list.get(i % list.size()).await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

    private static class Task implements Runnable {
        private final int i;

        public Task(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            work(i);
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < list.size(); i++)
            exec.execute(new Task(i));
    }
}
