package com.mastery.java.task.dao;

import com.mastery.java.task.dao.impl.EmployeeDAOImpl;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.time.LocalDate;
import java.util.List;

public class EmployeeDaoTest {
    private EmbeddedDatabase database;
    private EmployeeDao employeeDao;
    private JdbcTemplate jdbcTemplate;
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

    @Before
    public void init() {
        database = new EmbeddedDatabaseBuilder()
                .addScript("db/schema.sql")
                .addScript("db/fill_data.sql")
                .setType(EmbeddedDatabaseType.H2).build();

        jdbcTemplate = new JdbcTemplate(database);
        employeeDao = new EmployeeDAOImpl(jdbcTemplate);
    }

    @After
    public void tearDown() {
        database.shutdown();
    }

    @Test
    public void allEmployees() {
        List<Employee> employees = employeeDao.allEmployees();
        Assert.assertNotNull(employeeDao.allEmployees());
        Assert.assertEquals(3, employees.size());
    }

    @Test
    public void findOneEmployeeById() {
        Assert.assertNotNull(employeeDao.findOneEmployeeById(1L));
    }

    @Test(expected = DataAccessException.class)
    public void findOneEmployeeByIdInvalid() {
        employeeDao.findOneEmployeeById(96L);
    }

    @Test
    public void saveNewEmployee() {
        employeeDao.saveNewEmployee(testEmployeeInstance);
        String sqlQueryForMaxId = "SELECT MAX(employee_id) FROM employee";
        long maxId = jdbcTemplate.queryForObject(sqlQueryForMaxId, Long.class);
        testEmployeeInstance.setId(maxId);
        Assert.assertEquals(testEmployeeInstance, employeeDao.findOneEmployeeById(maxId));
    }

    @Test
    public void updateEmployee() {
        Employee employee = employeeDao.findOneEmployeeById(1L);
        String newJobTitle = "Software developer";
        employee.setJobTitle(newJobTitle);
        employeeDao.updateEmployee(employee);
        Assert.assertEquals(newJobTitle, employeeDao.findOneEmployeeById(1L).getJobTitle());
    }

    @Test
    public void deleteEmployee() {
        List<Employee> employees = employeeDao.allEmployees();
        employeeDao.deleteEmployee(2L);
        Assert.assertEquals(employees.size() - 1, employeeDao.allEmployees().size());
    }

    @Test
    public void deleteEmployeeWithInvalidId() {
        List<Employee> employees = employeeDao.allEmployees();
        Long invalidId = 96L;
        employeeDao.deleteEmployee(invalidId);
        Assert.assertEquals(employees.size(), employeeDao.allEmployees().size());
    }

    @Test
    public void findEmployeeByAllCredentials() {
        Employee employee = employeeDao.findOneEmployeeById(1L);
        Assert.assertNotNull(employeeDao.findEmployeeByAllCredentials(employee));
    }

    @Test(expected = DataAccessException.class)
    public void findEmployeeByAllCredentialsIsNotFound() {
        employeeDao.findEmployeeByAllCredentials(testEmployeeInstance);
    }
}

