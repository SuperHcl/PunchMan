package com.umpaytest.thread.pool;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 线程池拒绝策略演示
 *
 * @author hucl
 * @date 2021/7/29 11:06 上午
 */
public class RejectPolicyDemo {
    public static void main(String[] args) {
//        Map<Integer, Integer> map = new ConcurrentHashMap<>(20);
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
        ThreadFactory tf = r -> new Thread(r, "test-thread-pool");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.SECONDS, workQueue, tf);
        for (int i = 0; i < 20; i++) {
            executor.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + ":执行任务");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                double v = ThreadLocalRandom.current().nextDouble();
//                int i1 = ThreadLocalRandom.current().nextInt(1, 1000);
//                Integer integer = map.putIfAbsent(i1, 1);
//                if (integer != null) {
//                    map.put(i1, ++integer);
//                }
            });
        }

    }


}
