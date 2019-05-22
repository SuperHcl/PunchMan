package com.umpaytest.annotation;

import java.lang.reflect.Method;

/**
 * @author: Hucl
 * @date: 2019/4/26 17:03
 * @description:
 */
public class InitRemarkClassFactory {

    public static User create() {
        User user = new User();

        Method[] methods = User.class.getMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Init.class)) {
                Init init = m.getAnnotation(Init.class);
                try {
                    m.invoke(user, init.value());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }


    public static void main(String[] args) {
        User user = InitRemarkClassFactory.create();
//        System.out.println(user.toString());
        System.out.println(user.getName());
    }
}
