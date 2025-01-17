package com.mastery.java.task.validator;

import com.mastery.java.task.annotation.Adult;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<Adult, LocalDate> {
    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(dob, today);
        return period.getYears() >= 18;
    }
}
