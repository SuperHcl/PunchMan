package com.umpaytest.controller;

import com.umpaytest.annotation.processor.SupermanProcessor;
import com.umpaytest.entity.SupermanBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/7/3 18:46
 * @description:
 */
@RestController
public class GenSupermanController {
    @Autowired
    private SupermanProcessor supermanProcessor;

    @GetMapping("/generate")
    public String generateApi(HttpServletRequest request) {
        List<SupermanBean> result = supermanProcessor.process(request);
        return result.toString();
    }
}
