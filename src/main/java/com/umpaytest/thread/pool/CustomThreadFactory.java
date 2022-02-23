package com.umpaytest.thread.pool;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工厂
 *
 * @author Hu.ChangLiang
 * @date 2022/2/23 1:52 下午
 */
public class CustomThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, formatTime() + "线程");
    }

    public String formatTime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(ZonedDateTime.now());
    }
}
