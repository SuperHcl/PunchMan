package com.umpaytest.thread.jdk8;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * java8 lambda创建异步线程
 *
 * @author Hu.ChangLiang
 * @date 2021/11/15 10:36 上午
 */
public class LambdaThreadDemo {

    @Test
    public void test_create() throws ExecutionException, InterruptedException {
        /*
         * 两种创建方式
         * 1. runAsync
         * 2. supplyAsync
         * 这两区别就是runAsync没有返回值，或者说返回值为null,supplyAsync有返回值
         */
        CompletableFuture<Void> run_async = CompletableFuture.runAsync(() -> System.out.println("run async"));

        CompletableFuture<String> supply_async = CompletableFuture.supplyAsync(() -> {
            System.out.println("supply async");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "supplyAsync";
        });

        // 阻塞等待，runAsync 的future 无返回值，输出null
        System.out.println("run async join() return value is " + run_async.join());

        // 阻塞等待
        System.out.println("supply join() return value is " + supply_async.join());

        // .get()获取
        String supplyAsyncResult = supply_async.get();
        System.out.println("supply get() return value is " + supplyAsyncResult);

        System.out.println("Main thread end...");
    }

    @Test
    public void test_serial_task() {
        /*
         * 任务串行执行
         * | TaskA | --> | TaskB | --> | TaskC |
         */

        // 1. thenRunAsync - 任务完成就运行action，不关心上一个Task的运行结果，无返回值
        CompletableFuture
                .supplyAsync(() -> "TaskA")
                .thenRunAsync(() -> System.out.println("1. then runAsync: Ok"));


        // 2. thenAcceptAsync - 任务完成就运行action，依赖上一个Task的运行结果，无返回值
        CompletableFuture
                .supplyAsync(() -> "2. thenAcceptAsync: TaskA - 2")
                .thenAcceptAsync(System.out::println);


        // 3. thenApplyAsync - 任务完成就运行action，依赖上一个Task的运行结果，有返回值
        CompletableFuture<String> applyAsync = CompletableFuture.supplyAsync(() -> "TaskA - 3 returned")
                .thenApplyAsync(data -> {
                    System.out.println("3. thenApplyAsync: " + data);
                    return "asdf";
                });
        System.out.println("任务串行执行，依赖上个task的运行结果并且有返回值：" + applyAsync.join());


        // 4. thenCompose - 任务完成后则运行fn，依赖上一个Task的运行结果，有返回值
        // 4-1 第一个异步任务，常量任务
        CompletableFuture<String> f = CompletableFuture.completedFuture("then compose|常量任务fn");

        CompletableFuture<String> then_compose_async = CompletableFuture.supplyAsync(() -> "task a -4 thenCompose")
                .thenComposeAsync(data -> {
                    System.out.println("4. thenCompose: " + data);
                    return f;
                });
        System.out.println(then_compose_async.join());

    }
}
