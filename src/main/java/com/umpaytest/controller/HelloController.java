package com.umpaytest.controller;

import com.umpaytest.annotation.Params;
import com.umpaytest.annotation.Superman;
import com.umpaytest.property.PropertySourceValueDemo;
import com.umpaytest.property.ReadByPropertySourceAndConfProperties;
import com.umpaytest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Map;

@RestController
@RequestMapping("/hello")
public class HelloController {
    //    @Autowired
//    private UserService userService1;
//
    @Autowired
    private Map<String, UserService> userServiceMap;

    @Resource
    private PropertySourceValueDemo valueDemo;

    @Resource
    private ReadByPropertySourceAndConfProperties confProperties;


    @GetMapping("/test")
    @Superman(methodName = "helloWorld")
    public String helloWorld() {
        System.out.println(userServiceMap.get("userService1").doSomething("test"));
        System.out.println("sout test");
        return "";
    }

    @GetMapping("/propertySourceValue")
    public String propertySourceValue() {
        return valueDemo.toString();
    }

    @GetMapping("/propertySourceByConfPro")
    public String propertySourceByConfPro() {
        return confProperties.toString();
    }

    @GetMapping("/requestParamTest")
    public String requestParamTest(@Params(value = "name", notEmpty = true, required = true, defaultValue = "Lisa") String name) {
        return "requestParamTest's name = " + name;
    }

    @GetMapping("/requestParamTest2")
    public String requestParamTest2(@Params(notEmpty = true, required = true, defaultValue = "Lisa") String name) {
        return "requestParamTest2's name = " + name;
    }

    @GetMapping("/sendMail")
    public String sendMail() throws MessagingException {
        return "mail send success";
    }

}
