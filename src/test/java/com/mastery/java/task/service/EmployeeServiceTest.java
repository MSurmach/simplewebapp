package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.repository.EmployeeRepository;
import com.mastery.java.task.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    private static final Employee TEST_EMPLOYEE_VALID;

    @BeforeEach
    void initBefore() {
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    static {
        TEST_EMPLOYEE_VALID = new Employee(
                "Maksim",
                "Surmach",
                Gender.MALE,
                7L,
                "Jr. Software Engineer",
                LocalDate.of(1994, 8, 17));
        TEST_EMPLOYEE_VALID.setId(1L);
    }

    @Test
    void deleteEmployee_whenEmployeeExists() {
        Long testId = 1L;
        when(employeeRepository.findById(testId)).thenReturn(Optional.of(TEST_EMPLOYEE_VALID));
        assertTrue(employeeService.deleteEmployee(testId));
    }

    @Test
    void deleteEmployee_whenEmployeeNotFound() {
        Long testId = 100L;
        when(employeeRepository.findById(testId)).thenReturn(Optional.empty());
        assertFalse(employeeService.deleteEmployee(testId));
    }
}