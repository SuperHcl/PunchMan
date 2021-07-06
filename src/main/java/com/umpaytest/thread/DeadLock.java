package com.umpaytest.thread;

import java.util.concurrent.TimeUnit;

/**
 * 模拟死锁的场景
 * “死锁” 是两个或两个以上的线程在执行过程中，互相持有对方所需要的资源，
 * 导致这些线程处于等待状态，无法继续执行。若无外力作用，它们都将无法推进下去，就进入了“永久”阻塞的状态。<br>
 * 产生原因：<br>
 * - 互斥：共享资源 X 和 Y 只能被一个线程占用<br>
 * - 占有且等待：线程01 已经取得共享资源 X，在等待共享资源 Y 的时候，不释放共享资源 X<br>
 * - 不可抢占：其他线程不能强行抢占线程01 占有的资源<br>
 * - 循环等待：线程01 等待线程02 占有的资源，线程02 等待线程01 占有的资源，就是循环等待<br>
 * @author hucl
 * @date 2021/7/6 2:03 下午
 */
public class DeadLock {

    public static final String obj1 = "a";
    public static final String obj2 = "b";

    public static void main(String[] args) {
        aLock();bLock();
    }


    private static void aLock() {
        System.out.println("[a]Lock ** will start");
        new Thread(()->{
            try {
                synchronized (obj1) {
                    System.out.println("[a]Lock ** locked obj1");
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (obj2) {
                        System.out.println("[a]Lock ** locked obj2");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, "a-lock").start();
    }

    private static void bLock() {
        System.out.println("[b]Lock--will start");
        new Thread(()->{
            try {
                synchronized (obj2) {
                    System.out.println("[b]Lock--locked obj2");
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (obj1) {
                        System.out.println("[b]Lock--locked obj1");
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }, "b-lock").start();
    }
}
