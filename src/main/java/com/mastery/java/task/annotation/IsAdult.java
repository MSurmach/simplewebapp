package com.mastery.java.task.annotation;

import com.mastery.java.task.validator.AgeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AgeValidator.class)

public @interface IsAdult {

    String message() default "Constraint violation. Age is less than 18 years!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
