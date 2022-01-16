package com.mastery.java.task.dao;

import com.mastery.java.task.dao.impl.EmployeeDAOImpl;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDaoTest {
    private final Employee testEmployeeInstance;
    private EmbeddedDatabase database;
    private EmployeeDao employeeDao;
    private JdbcTemplate jdbcTemplate;

    {
        testEmployeeInstance = new Employee(
                "Kate",
                "Peterson",
                Gender.FEMALE,
                4L,
                "Secretary",
                LocalDate.of(1986, 9, 15));
    }

    @BeforeEach
    public void init() {
        database = new EmbeddedDatabaseBuilder()
                .addScript("db/schema.sql")
                .addScript("db/fill_data.sql")
                .setType(EmbeddedDatabaseType.H2).build();

        jdbcTemplate = new JdbcTemplate(database);
        employeeDao = new EmployeeDAOImpl(jdbcTemplate);
    }

    @AfterEach
    public void tearDown() {
        database.shutdown();
    }

    @Test
    public void allEmployees() {
        List<Employee> employees = employeeDao.allEmployees();
        assertNotNull(employeeDao.allEmployees());
        assertEquals(3, employees.size());
    }

    @Test
    public void findOneEmployeeById() {
        assertNotNull(employeeDao.findOneEmployeeById(1L));
    }

    @Test
    public void findOneEmployeeByIdInvalid() {
        assertThrows(DataAccessException.class, () -> employeeDao.findOneEmployeeById(96L));
    }

    @Test
    public void saveNewEmployee() {
        employeeDao.saveNewEmployee(testEmployeeInstance);
        String sqlQueryForMaxId = "SELECT MAX(employee_id) FROM employee";
        long maxId = jdbcTemplate.queryForObject(sqlQueryForMaxId, Long.class);
        testEmployeeInstance.setId(maxId);
        assertEquals(testEmployeeInstance, employeeDao.findOneEmployeeById(maxId));
    }

    @Test
    public void updateEmployee() {
        Long employeeId = 1L;
        Employee employee = employeeDao.findOneEmployeeById(employeeId);
        String newJobTitle = "Software developer";
        employee.setJobTitle(newJobTitle);
        employeeDao.updateEmployee(employeeId, employee);
        assertEquals(newJobTitle, employeeDao.findOneEmployeeById(1L).getJobTitle());
    }

    @Test
    public void deleteEmployee() {
        List<Employee> employees = employeeDao.allEmployees();
        employeeDao.deleteEmployee(2L);
        assertEquals(employees.size() - 1, employeeDao.allEmployees().size());
    }

    @Test
    public void deleteEmployeeWithInvalidId() {
        List<Employee> employees = employeeDao.allEmployees();
        Long invalidId = 96L;
        employeeDao.deleteEmployee(invalidId);
        assertEquals(employees.size(), employeeDao.allEmployees().size());
    }

    @Test
    public void findEmployeeByAllCredentials() {
        Employee employee = employeeDao.findOneEmployeeById(1L);
        assertNotNull(employeeDao.findEmployeeByAllCredentials(employee));
    }

    @Test
    public void findEmployeeByAllCredentialsIsNotFound() {
        assertThrows(DataAccessException.class, () ->  employeeDao.findEmployeeByAllCredentials(testEmployeeInstance));
    }
}

