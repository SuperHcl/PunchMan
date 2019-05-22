package com.umpaytest.annotation;

import lombok.Data;

/**
 * @author: Hucl
 * @date: 2019/4/26 17:01
 * @description:
 */
@Data
public class User {


    private String name;

    private String address;

    private String age;

    @Init(value = "小红")
    public void setName(String name) {
        this.name = name;
    }
    @Init(value = "beijing")
    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
