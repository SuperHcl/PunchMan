package com.umpaytest.annotation;

import lombok.Data;

/**
 * @author: Hucl
 * @date: 2019/10/21 11:16
 * @description:
 */
@Data
public class InitAnnotationBean {

    @Init(value = "init annotation")
    private String name;

    @Init(value = "hadn't name")
    private String method;

    @Init(value = "1")
    public Integer number;
}
