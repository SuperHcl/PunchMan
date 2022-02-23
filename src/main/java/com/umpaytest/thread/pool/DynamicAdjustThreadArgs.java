package com.umpaytest.thread.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 动态调整线程池的相关参数
 *
 * @author Hu.ChangLiang
 * @date 2022/2/23 1:47 下午
 */
public class DynamicAdjustThreadArgs {

    public static void main(String[] args) throws InterruptedException {
        dynamicModifyExecutor();
    }

    private static ThreadPoolExecutor build() {
        return new ThreadPoolExecutor(2,
                5,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10),
                new CustomThreadFactory());
    }

    private static void dynamicModifyExecutor() throws InterruptedException {
        ThreadPoolExecutor executor = build();
        for (int i = 0; i < 15; i++) {
            executor.execute(() -> {
                printExecutorStatus(executor, "创建任务~");
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        printExecutorStatus(executor, "改变之前");
        // modify executors
        executor.setCorePoolSize(10);
        executor.setMaximumPoolSize(10);
        printExecutorStatus(executor, "改变之后");
        Thread.currentThread().join();
    }

    private static void printExecutorStatus(ThreadPoolExecutor executor, String name) {
        BlockingQueue<Runnable> queue = executor.getQueue();
        System.out.println(Thread.currentThread().getName() + "-" + name + "-:" +
                "核心线程数:" + executor.getPoolSize() +
                " 活动线程数:" + executor.getActiveCount() +
                " 最大线程数:" + executor.getMaximumPoolSize() +
                " 线程活跃度:" + divide(executor.getActiveCount(), executor.getMaximumPoolSize()) +
                " 任务完成数:" + executor.getCompletedTaskCount() +
                " 队列大小:" + (queue.size() + queue.remainingCapacity()) +
                " 当前排队线程数:" + queue.size() +
                " 队列剩余容量:" + queue.remainingCapacity() +
                " 队列使用情况:" + divide(queue.size(), queue.size()+queue.remainingCapacity())
        );
    }

    private static String divide(int num1, int num2) {
        return String.format("%1.2f%%",
                Double.parseDouble(String.valueOf(num1)) / Double.parseDouble(String.valueOf(num2)) * 100);
    }
}
