package com.umpaytest.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Hucl
 * @date: 2019/7/3 18:12
 * @description: 自定义注解
 */
@Data
@Accessors(chain = true)    // 链式编程
public class SupermanBean {

    private String name;

    /**
     * 接口相对URL
     */
    private String apiUrl;

    /**
     * DELETE、GET/POST ...
     */
    private String requestMethodName;
}
