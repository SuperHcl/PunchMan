package com.umpaytest.springProxy;


import com.umpaytest.springProxy.service.Test;
import com.umpaytest.springProxy.service.UserManager;
import com.umpaytest.springProxy.service.UserManagerImpl;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: Hucl
 * @date: 2019/6/24 19:19
 * @description: cglib动态代理
 *
 * JDK动态代理和CGLIB字节码生成的区别？
 *  （1）JDK动态代理只能对实现了接口的类生成代理，而不能针对类
 *  （2）CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法
 *   因为是继承，所以该类或方法最好不要声明成final
 */
public class CglibDynamicProxy implements MethodInterceptor {
    // 需要代理的目标对象
    private Object target;

    // 重写拦截方法
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Cglib动态代理，监听开始！");
        Object invoke = method.invoke(target, objects);
        System.out.println("Cglib动态代理，监听结束！");
        return invoke;
    }

    public Object getCglibProxy(Object targetObject) {
        this.target = targetObject;
        Enhancer enhancer = new Enhancer();
        //设置父类,因为Cglib是针对指定的类生成一个子类，所以需要指定父类
        enhancer.setSuperclass(targetObject.getClass());
        enhancer.setCallback(this);// 设置回调
        return enhancer.create();//创建并返回代理对象;
    }

    public static void main(String[] args) {
        CglibDynamicProxy cglibProxy = new CglibDynamicProxy();
        // 代理实现接口的类
        UserManager userManager = (UserManager) cglibProxy.getCglibProxy(new UserManagerImpl());
        userManager.delUser("delete");

        // 代理实现普通类
        Test test = (Test) cglibProxy.getCglibProxy(new Test());
        test.test("hahah");
    }
}
