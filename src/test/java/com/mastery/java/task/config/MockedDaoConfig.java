package com.mastery.java.task.config;

import com.mastery.java.task.dao.EmployeeDao;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockedDaoConfig {

    @Bean
    public EmployeeDao employeeDao() {
        return Mockito.mock(EmployeeDao.class);
    }
}
