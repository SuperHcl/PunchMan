package com.umpaytest.springProxy.reflect;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Hu.ChangLiang
 * @date 2021/12/23 4:27 下午
 */
@Service
public class ReflectService {

    public String oneInt(int num) {
        return "oneIntMethod - " + num;
    }

    public void twoString(String str) {
        System.out.println("twoStringMethod - " + str);
    }


    public Map<String, Object> threeMap(Map<String, Object> param) {
        param.put("threeMap", ReflectService.class);
        return param;
    }

}
