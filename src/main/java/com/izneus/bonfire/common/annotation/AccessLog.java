package com.izneus.bonfire.common.annotation;

import java.lang.annotation.*;

/**
 * api访问日志注解
 *
 * @author Izneus
 * @date 2020/08/06
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLog {
    String value() default "";
}
