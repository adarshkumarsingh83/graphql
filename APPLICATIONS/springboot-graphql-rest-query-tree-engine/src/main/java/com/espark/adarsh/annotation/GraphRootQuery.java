package com.espark.adarsh.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface GraphRootQuery {

    String value() default "";

}
