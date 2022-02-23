package com.umpaytest.annotation;

import java.lang.annotation.*;

/**
 * 参数验证
 * 作用类似于@RequestParam
 * 不过RequestParam 在传空字符的情况下，也会通过
 *
 * @author hucl
 * @date 2021/9/18 4:12 下午
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Params {

    String value() default "";

    boolean required() default true;

    boolean notEmpty() default true;

    String defaultValue() default "";
}
