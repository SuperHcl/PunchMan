package com.umpaytest.init;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Hu.ChangLiang
 * @date 2025/2/11 14:09
 */
@Component
public class DataLoader {

    @EventListener(ApplicationReadyEvent.class)
    @Order(1)
    public void loadData() {
        System.out.println("在应用完全启动后，开始加载数据...2");
    }
}
