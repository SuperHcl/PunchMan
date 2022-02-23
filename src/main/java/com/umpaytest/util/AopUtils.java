package com.umpaytest.util;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hu.ChangLiang
 * @date 2021/9/28 5:43 下午
 */
public class AopUtils {
    private AopUtils() {
    }

    public static Method getMethodFromTarget(JoinPoint joinPoint) {
        Method method = null;
        if (joinPoint.getSignature() instanceof MethodSignature) {
            MethodSignature ms = (MethodSignature) joinPoint.getSignature();
            Object target = joinPoint.getTarget();
            try {
                method = target.getClass().getMethod(ms.getName(), ms.getParameterTypes());
            } catch (NoSuchMethodException e) {
                // ignore
            }
        }
        return method;
    }

    public static void main(String[] args) {

        List<String> list = null;

        for (String s : list) {
            System.out.println(s);
        }

    }
}
