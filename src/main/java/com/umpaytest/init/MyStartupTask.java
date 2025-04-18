package com.umpaytest.init;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Hu.ChangLiang
 * @date 2025/2/11 14:07
 */
@Component
public class MyStartupTask {

    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    public void onApplicationReady() {
        System.out.println("应用已经完全启动起来了1");
    }

}
