package com.umpaytest.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.umpaytest.entity.Test;
import com.umpaytest.entity.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/5/6 17:47
 * @description:
 */
public class JsonTest {


    public static void object2Json() {
//        Test test = new Test("","A()");
//        System.out.println(JSONObject.toJSONString(test));
        User user = new User();
        user.setAddress("beijing");
        user.setAge(10);
        user.setName("hcl");
        System.out.println(JSONObject.toJSONString(user, SerializerFeature.WriteNullStringAsEmpty));

    }
    public static void main(String[] args) {
        object2Json();
        String json = "{\"attribute\": \"beijing\",\"method\": \"BEIJING()\"}";
        String json1 = "{\"address\":\"beijing\",\"age\":10,\"email\":\"\",\"name\":\"hcl\"}";
//        json2Object();
//        List<Integer> integers = Arrays.asList(1,3,4,5,6);
//        System.out.println(integers);
//        Map<String, String> map = new HashMap<>();
//        map.put("1","1");
//        map.put("2","3");
//        map.put("4","4");
//        map.putIfAbsent("1","3");   //如果key存在 则不填充value值，key不存在则填充value值
//
//        System.out.println(map);

    }


}
