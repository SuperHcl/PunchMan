package com.umpaytest.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author: Hucl
 * @date: 2019/4/26 17:03
 * @description:
 */
public class InitRemarkClassFactory {

    /**
     * 解析@Init作用在Setting方法上，并赋值。
     * Method.invoke(Object, args)
     * @return
     */
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

    /**
     * 解析@Init使用是field上，并给field赋值
     * @return
     */
    public static InitAnnotationBean fieldTest() {
        InitAnnotationBean initAnnotationBean = new InitAnnotationBean();

        /**
         * Class.getDeclaredFields() 和 Class.getFields()的区别：
         * getDeclaredFields 获取的是所有访问类型的属性，不包括被继承的属性。
         * getFields 只能获取public类型的属性
         */
        Field[] fields = InitAnnotationBean.class.getDeclaredFields();
        for (Field field : fields)
            if (field.isAnnotationPresent(Init.class)) {
                Class<?> classType = field.getType();
                Init annotation = field.getAnnotation(Init.class);
                field.setAccessible(true);
                try {
                    String value = annotation.value();
                    if (classType == Integer.class) {
                        field.set(initAnnotationBean, Integer.valueOf(value));
                    } else {
                        field.set(initAnnotationBean, value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        return initAnnotationBean;
    }

    public static void main(String[] args) {
        User user = InitRemarkClassFactory.create();
        System.out.println(user.toString());
        System.out.println(user.getName());

        InitAnnotationBean bean = InitRemarkClassFactory.fieldTest();
        System.out.println(bean);
    }
}
