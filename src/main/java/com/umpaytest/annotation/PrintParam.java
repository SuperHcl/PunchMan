package com.umpaytest.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * API请求入参出参打印
 *
 * @author Hu.ChangLiang
 * @date 2021/9/28 5:06 下午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PrintParam {

    /*
     * 是否打印入参
     */
    boolean inputArgs() default true;

    /*
     * 是否打印出参
     */
    boolean outputArgs() default true;

    /*
     * 方法中文名称
     */
    @AliasFor("name")
    String methodChName() default "";

    String name() default "";
}
