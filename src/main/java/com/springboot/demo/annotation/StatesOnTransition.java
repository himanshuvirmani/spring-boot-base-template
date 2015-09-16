package com.springboot.demo.annotation;

import com.springboot.demo.domain.enums.HotelStatusEnum;
import org.springframework.statemachine.annotation.OnTransition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by himanshu.virmani on 16/09/15.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnTransition
public @interface StatesOnTransition {

    HotelStatusEnum[] source() default {};

    HotelStatusEnum[] target() default {};

}
