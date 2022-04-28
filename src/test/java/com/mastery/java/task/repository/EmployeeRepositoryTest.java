package com.mastery.java.task.repository;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    private final Employee testEmployeeInstance;

    {
        testEmployeeInstance = new Employee(
                "Kate",
                "Peterson",
                Gender.FEMALE,
                4L,
                "Secretary",
                LocalDate.of(1986, 9, 15));
    }

    @Test
    void findByFirstnameContainsAndLastnameContainsAllIgnoreCase_onlyWithNameTest() {
        entityManager.persist(testEmployeeInstance);
        entityManager.flush();
        final String firstName = "ka";
        final String lastName = "son";
        List<Employee> foundEmployees = employeeRepository.findByFirstnameContainsAndLastnameContainsAllIgnoreCase(firstName, lastName);
        System.out.println();
        System.out.println(foundEmployees.get(0).toString());
        System.out.println();
    }
}