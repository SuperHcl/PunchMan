package com.umpaytest.springProxy.reflect;

/**
 * @author: Hucl
 * @date: 2019/6/25 16:57
 * @description:
 */
public class ReflectDemo {

    public String name;

    public ReflectDemo(String name) {
        this.name = name;
    }

    public String selectOne(String id) {
        return "J " + id;
    }
}
