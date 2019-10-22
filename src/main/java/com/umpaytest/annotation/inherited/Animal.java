package com.umpaytest.annotation.inherited;

import java.lang.annotation.*;

/**
 * @author: Hucl
 * @date: 2019/9/19 10:38
 * @description:
 *
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Animal {

    String name() default "";
}
