package com.umpaytest.test;

/**
 * @author: Hucl
 * @date: 2019/4/26 15:01
 * @description:
 */
public class TestVolatile {

    public static void main(String[] args) {
        System.out.printf("%s","active count: "+Thread.activeCount()+"--"+System.currentTimeMillis()+"\n");
        ThreadDemo threadDemo = new ThreadDemo();
//        Thread thread = new Thread(threadDemo);
//        thread.setPriority(6);
//        thread.start();
        new Thread(threadDemo).start();

        System.out.printf("%s","active count: "+Thread.activeCount()+"--"+System.currentTimeMillis()+"\n");
        while (true) {
            if (threadDemo.isFlag()) {
//                System.out.println("Thread name---"+Thread.currentThread().getName());
//                System.out.println("Thread priority---"+Thread.currentThread().getPriority());
//                System.out.println("ThreadDemo is Daemon---"+Thread.currentThread().isDaemon());
                System.out.println("-----------");
                break;
            }
        }

    }


}
class ThreadDemo implements Runnable {
    private volatile boolean flag = false;

    @Override
    public void run() {

//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("ThreadDemo name---"+Thread.currentThread().getName());
//        System.out.println("ThreadDemo priority---"+Thread.currentThread().getPriority());
//        System.out.println("ThreadDemo is Daemon---"+Thread.currentThread().isDaemon());
        flag = true;
        System.out.println("flag="+isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    private void setFlag(boolean flag) {
        this.flag = flag;
    }
}
