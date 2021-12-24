package com.umpaytest.controller;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Hu.ChangLiang
 * @date 2021/11/24 10:22 上午
 */
public interface DemoApi {

    @GetMapping("/demoApi/getAge")
    String getAge(String name);
}
