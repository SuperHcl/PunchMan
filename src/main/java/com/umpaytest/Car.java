package com.umpaytest;


import java.io.Serializable;

/**
 * @author: Hucl
 * @date: 2019/4/16 18:01
 * @description:
 */
public class Car implements Serializable {
    private static final long serialVersionID = 1L;
    private static String AGE = "111";
    private String name;
    transient private String car;

    public static long getSerialVersionID() {
        return serialVersionID;
    }

    public static String getAGE() {
        return AGE;
    }

    public static void setAGE(String AGE) {
        Car.AGE = AGE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", car='" + car + '\'' +
                ", AGE='" + AGE + '\'' +
                '}';
    }
}
