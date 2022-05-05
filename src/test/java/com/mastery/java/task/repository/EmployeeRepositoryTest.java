package com.mastery.java.task.repository;

import com.mastery.java.task.dto.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
    }

    @Test
    void getEmployeesFromTheDB() {
        List<Employee> all = employeeRepository.findAll();
        assertThat(all.size()).isGreaterThan(0);
    }

    @Test
    void findByFirstnameContainsAndLastnameContainsAllIgnoreCase_onlyWithNameTest() {

    }
}