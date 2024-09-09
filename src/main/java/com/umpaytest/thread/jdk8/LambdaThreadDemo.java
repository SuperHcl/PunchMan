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


    @Test
    public void test_multi_process() {
        /*
         * 并行
         * | TaskA |-------
         *                 | ----> | TaskC |
         * | TaskB |-------
         */
        // 1. 两个completableFuture执行完，然后执行action，不依赖两个任务的执行结果，无返回值
        // 第一个异步任务
        CompletableFuture<String> taskA = CompletableFuture.completedFuture("TaskA");
        // 第二个
        CompletableFuture.supplyAsync(() -> "TaskB")
                // 两个都执行完后，再执行action
                .runAfterBothAsync(taskA, () -> System.out.println("runAfterBothAsync() is Ok"));

        // 2. 两个completableFuture并行执行完，然后执行action，依赖两个任务的执行结果，无返回值
        CompletableFuture.supplyAsync(() -> "thenAcceptBothAsync() ...")
                .thenAcceptBothAsync(taskA, (task_b, task_a) -> {
                    System.out.println(task_a);
                    System.out.println(task_b);
                });

        // 3. 两个completableFuture并行执行完，然后执行fn，依赖上两个任务的执行结果，有返回值
        CompletableFuture<String> combineAsync = CompletableFuture.supplyAsync(() -> "thenCombineAsync() ...")
                .thenCombineAsync(taskA, (b, a) -> {
                    System.out.println(b);
                    return "combine async...";
                });
        System.out.println(combineAsync.join());
    }

    @Test
    public void test_any_task_run() {
        /*
         * 线程并行执行，谁先执行完则触发一下任务（两者选其最快）
         */
        // 1. 上一个任务或者other任务完成, 运行action，不依赖前一任务的结果，无返回值
        CompletableFuture<String> firstTask = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("first task running...");
            return "first task";
        });
        CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("runAfterEitherAsync() second task running...");
                    return "second task";
                })
                .runAfterEitherAsync(firstTask, () -> System.out.println("runDone"));

        // 2. 上一个任务或者other任务完成, 运行action，依赖最先完成任务的结果，无返回值
        CompletableFuture.supplyAsync(() -> {
            System.out.println("acceptEitherAsync() second task running...");
            return "acceptEitherAsync";
        }).acceptEitherAsync(firstTask, System.out::println);

        // 3. 上一个任务或者other任务完成, 运行fn，依赖最先完成任务的结果，有返回值
        CompletableFuture<String> applyToEitherAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println("applyToEitherAsync() second task running");
            return "applyToEitherAsync";
        }).applyToEitherAsync(firstTask, (data) -> {
            System.out.println(data);
            return "applyToEitherAsync method return";
        });

        System.out.println(applyToEitherAsync.join());
    }

    @Test
    public void test_exceptional_handle() throws ExecutionException, InterruptedException {
        /*
         * 异常处理
         */
        // 1. exceptionally
        CompletableFuture<Integer> exceptionally = CompletableFuture.supplyAsync(() -> {
            // 模拟异常
            System.out.println(1 / 0);
            return 22;
        }).thenApply(data -> data + 1)
                // 异常处理，前两块异常都能处理，supplyAsync & thenApply
                .exceptionally(e -> {
                    System.out.println(e);
                    return 0;
                });
        System.out.println(exceptionally.get());

        // 2. handle-任务完成或者异常时运行fn，返回值为fn的返回
        CompletableFuture<Integer> custom_exception = CompletableFuture.supplyAsync(() -> {
            if (true) {
//                throw new RuntimeException("custom exception");
            }
            return 11;
        }).thenApply(data -> data * 6)
                .handleAsync((data, e) -> {
                    // 异常信息不会在外层暴露
                    System.out.println("异常信息=" + e);
                    // 如果有异常，最终返回的结果是0，int
                    return data;
                });
        System.out.println(custom_exception.join());
    }

    @Test
    public void test_exception_whenComplete() {
        /*
         * whenComplete() 处理异常
         * 异常信息在whenComplete()处理过后，还会暴露到外层
         * 并且没有返回值
         */
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            System.out.println(1 / 0);
            return 99;
        }).thenApply(data -> data / 2)
                .whenComplete((data, e) -> {
                    if (e != null) {
                        System.out.println("异常信息--" + e);
                    }
                });

        System.out.println(integerCompletableFuture.join());
    }

    @Test
    public void test_multi_task_composite_allOf() {
        /*
         * 多任务组合
         * allOf 所有
         * anyOf 任意
         */

        CompletableFuture<Void> task_one = CompletableFuture.runAsync(() -> System.out.println("all of task one"));
        CompletableFuture<String> task_two = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("all of task two");return "all of task two";
        });
        // 全部任务完成
        CompletableFuture<Void> allOf = CompletableFuture.allOf(task_one, task_two);
        System.out.println(allOf.join());

    }

    @Test
    public void test_multi_task_composite_anyOf() {
        CompletableFuture<Void> task_one = CompletableFuture.runAsync(() -> System.out.println("any of task one"));
        CompletableFuture<String> task_two = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("any of task two");return "any of task two";
        });
        // 任意一个执行完就可
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(task_one, task_two);
        System.out.println(anyOf.join());
    }
}
