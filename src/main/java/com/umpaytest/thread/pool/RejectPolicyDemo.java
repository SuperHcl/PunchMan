package com.umpaytest.thread.pool;

import java.util.concurrent.*;

/**
 * 线程池拒绝策略演示
 *
 * @author hucl
 * @date 2021/7/29 11:06 上午
 */
public class RejectPolicyDemo {
    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
        ThreadFactory tf = r -> new Thread(r, "test-thread-pool");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.SECONDS, workQueue, tf);
        for (int i = 0; i < 20; i++) {
            executor.submit(()->{
                try {
                    System.out.println(Thread.currentThread().getName() + ":执行任务");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }


}
