package com.umpaytest.init;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Hu.ChangLiang
 * @date 2025/2/11 14:07
 */
@Configuration
public class AyStartupTask {

    @EventListener(ApplicationReadyEvent.class)
//    @Order(1)
    public void a2() {
        System.out.println("应用已经完全启动起来了1");
    }



    @EventListener(ApplicationReadyEvent.class)
//    @Order(1)
    public void a3() {
        System.out.println("应用已经完全启动起来了11");
    }


    @EventListener(ApplicationReadyEvent.class)
//    @Order(1)
    public void a1() {
        System.out.println("应用已经完全启动起来了111");
    }

}
