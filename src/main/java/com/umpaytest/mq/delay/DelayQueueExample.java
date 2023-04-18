package com.umpaytest.mq.delay;

import com.umpaytest.util.TimeUtil;

import java.util.Date;
import java.util.concurrent.DelayQueue;

/**
 * 延时队列例子
 *
 * @author Hu.ChangLiang
 * @date 2023/4/18 14:43
 */
public class DelayQueueExample {
    static final String format = "yyyy-MM-dd HH:mm:ss.sss";

    public static void main(String[] args) throws InterruptedException {

        // 创建 DelayQueue
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();

        // 添加延时任务
        delayQueue.put(new DelayedTask("Task1", 2000));
        delayQueue.put(new DelayedTask("Task2", 5000));
        delayQueue.put(new DelayedTask("Task3", 3000));
        System.out.println("插入完毕：" + TimeUtil.format(new Date(), format));

        // 取出延时任务并执行
        while (!delayQueue.isEmpty()) {
            DelayedTask task = delayQueue.take();
            System.out.println("Time is " + TimeUtil.format(new Date(), format) + " Execute task: " + task);
        }
    }
}
