package com.mastery.java.task.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AgeValidatorTest {

    private final AgeValidator ageValidator;

    {
        ageValidator = new AgeValidator();
    }

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void isValid_shouldReturnFalseWhenAgeIsLessThan18() {
        LocalDate notValidBirthday = LocalDate.of(2008, 1, 1);
        assertFalse(ageValidator.isValid(notValidBirthday, constraintValidatorContext));
    }

    @Test
    public void isValid_shouldReturnFalseWhenAgeIs18() {
        LocalDate notValidBirthday = LocalDate.of(1994, 1, 1);
        assertTrue(ageValidator.isValid(notValidBirthday, constraintValidatorContext));
    }

}