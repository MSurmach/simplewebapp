package com.mastery.java.task.validator;

import org.junit.Test;

import java.time.LocalDateTime;

class AgeValidatorTest {

    @Test
    public void isValid() {
        LocalDateTime today = LocalDateTime.now();
        System.out.println(today);
    }

}