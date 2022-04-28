package com.mastery.java.task.service.impl;

import com.mastery.java.task.repository.EmployeeRepository;
import com.mastery.java.task.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    @TestConfiguration
    static class EmployeeServiceTestConfiguration {
        @MockBean
        private EmployeeRepository employeeRepository;

        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl(employeeRepository);
        }
    }

    @BeforeAll
    void setUp() {

    }

    @Test
    void findEmployeeById() {

    }

    @Test
    void findEmployeesByName() {
    }

    @Test
    void saveEmployee() {
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployee() {
    }
}