package com.umpaytest.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.umpaytest.annotation.PrintParam;
import com.umpaytest.util.AopUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 出入参打印切面
 *
 * @author Hu.ChangLiang
 * @date 2021/9/28 5:05 下午
 */
@Slf4j
@Aspect
//@Component
public class PrintParamAspect {


    @Pointcut("execution( * com.umpaytest.*.*(..)) && @annotation(com.umpaytest.annotation.PrintParam)")
    public void printParamPointcut(){}

    @Around(value = "printParamPointcut()")
    public Object printParam(final ProceedingJoinPoint joinPoint) throws Throwable {
        Method methodFromTarget = AopUtils.getMethodFromTarget(joinPoint);
        if (methodFromTarget == null) {
            return joinPoint.proceed();
        }
        final PrintParam printParamAnnotation = methodFromTarget.getAnnotation(PrintParam.class);
        String methodName = methodFromTarget.getName();
        Object[] args = joinPoint.getArgs();
        args = Arrays.stream(args).filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse))).toArray();

        StopWatch watch = new StopWatch();
        // 耗时记载
        watch.start();
        log.info(String.format("方法调用开始: 方法名:[%s], 入参: %s", methodName, printParamAnnotation.inputArgs() ? formatParam(args) : null));
        Object result = joinPoint.proceed();
        watch.stop();
        log.info(String.format("方法调用结束: 方法名:[%s],调用时间:[%dms], 回参: %s", methodName, watch.getTime(), printParamAnnotation.outputArgs() ? formatParam(result) : null));
        return result;
    }

    /**
     * 格式化参数
     */
    private String formatParam(Object result) {
        String afterResult = "内容格式化失败";
        try {
            afterResult = JSONObject.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            // ignore
        }
        return afterResult;
    }
}
