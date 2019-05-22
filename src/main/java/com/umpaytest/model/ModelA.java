package com.umpaytest.model;

/**
 * @author: Hucl
 * @date: 2019/5/9 10:12
 * @description:
 */
public class ModelA {

    private static String monitorInfo;

    public String process(String request) {
        monitorInfo = request;
        return "100";
    }

    public String getMonitorLog() {
        return monitorInfo;
    }


    public static void main(String[] args) {
        ModelA a = new ModelA();
        String score = a.process("modelA");
        System.out.println(score);

        String log = a.getMonitorLog();
        System.out.println(log);
    }


}
