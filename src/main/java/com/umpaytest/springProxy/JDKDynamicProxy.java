package com.umpaytest.springProxy;


import com.umpaytest.springProxy.service.Test;
import com.umpaytest.springProxy.service.UserManager;
import com.umpaytest.springProxy.service.UserManagerImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: Hucl
 * @date: 2019/6/24 19:04
 * @description: jdk动态代理
 *
 * JDK动态代理和CGLIB字节码生成的区别？
 *  （1）JDK动态代理只能对实现了接口的类生成代理，而不能针对类
 *  （2）CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法
 *   因为是继承，所以该类或方法最好不要声明成final
 */
public class JDKDynamicProxy implements InvocationHandler {
    // 需要代理的目标对象
    private Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("JDK动态代理，监听开始！");
        Object result = method.invoke(target, args);
        System.out.println("JDK动态代理，监听结束！");
        return result;
    }
    //定义获取代理对象方法
    private Object getJDKProxy(Object targetObject) {
        this.target = targetObject;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);

    }


    public static void main(String[] args) {
        // 代理实现接口的类
        JDKDynamicProxy jdkDynamicProxy = new JDKDynamicProxy();
        UserManager userManager = (UserManager) jdkDynamicProxy.getJDKProxy(new UserManagerImpl());
        userManager.addUser("test", "123456");

        // 实现普通类 error
        // Exception in thread "main" java.lang.ClassCastException: com.sun.proxy.$Proxy1 cannot be cast to com.
        // umpaytest.springProxy.service.Test
        Test test = (Test) jdkDynamicProxy.getJDKProxy(new Test());
        test.test("a");
    }
}
