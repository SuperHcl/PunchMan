package com.umpaytest.entity;

import lombok.Data;

/**
 * @author: Hucl
 * @date: 2019/3/22 11:17
 * @description: test
 */
@Data
public class Test {
    private String attribute;
    private String method;
    private static Test test = null;
    public Test(){}

    public Test(String attribute, String method) {
        this.attribute = attribute;
        this.method = method;
    }

    @Override
    public String toString() {
        return "Test{" +
                "attribute='" + attribute + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
    /*
     * @author Hucl
     * @description
     * @date 16:22 2019/3/22
     * @param []
     * @return com.umpaytest.entity.Test
     **/
    private static Test getInstance() {
        if (test == null) {
            test = new Test();
        }
        return test;
    }

}
