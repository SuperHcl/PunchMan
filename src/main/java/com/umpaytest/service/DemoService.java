package com.umpaytest.service;

import com.umpaytest.controller.DemoApi;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hu.ChangLiang
 * @date 2021/11/24 10:23 上午
 */
@RestController
public class DemoService implements DemoApi {
    @Override
    public String getAge(@RequestParam("name")String name) {
        return name + 18;
    }
}
