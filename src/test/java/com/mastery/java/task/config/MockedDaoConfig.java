package com.mastery.java.task.config;

import com.mastery.java.task.dao.EmployeeDao;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for DAO layer.
 */
@Configuration
public class MockedDaoConfig {

    /**
     * Create DAO layer.
     *
     * @return "plugged" DAO instance, powered by Mockito library.
     */
    @Bean
    public EmployeeDao employeeDao() {
        return Mockito.mock(EmployeeDao.class);
    }
}
