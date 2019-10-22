package com.umpaytest.annotation.inherited;

import java.lang.annotation.*;

/**
 * @author: Hucl
 * @date: 2019/9/19 11:00
 * @description:
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Flower {

    String name() default "";
}
