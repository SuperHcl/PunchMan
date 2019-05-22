package com.umpaytest.annotation;

import java.lang.annotation.*;

/**
 * @author: Hucl
 * @date: 2019/4/26 16:59
 * @description:
 */
@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Init {

    String value() default "";
}
