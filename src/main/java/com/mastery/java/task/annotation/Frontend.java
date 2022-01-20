package com.mastery.java.task.annotation;

import com.mastery.java.task.condition.FrontendCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Conditional(FrontendCondition.class)
public @interface Frontend {
}
