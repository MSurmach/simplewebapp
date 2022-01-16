package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dao.impl.EmployeeDAOImpl;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.exceptions.EmployeeIsAlreadyExisted;
import com.mastery.java.task.exceptions.EmployeeIsNotFoundException;
import com.mastery.java.task.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    private final Employee testEmployeeInstance;
    private EmployeeDao employeeDaoMock;
    private EmployeeService employeeService;

    {
        testEmployeeInstance = new Employee(
                "Kate",
                "Peterson",
                Gender.FEMALE,
                4L,
                "Secretary",
                LocalDate.of(1986, 9, 15));
        testEmployeeInstance.setId(1L);
    }

    @BeforeAll
    public void init() {
        employeeDaoMock = mock(EmployeeDAOImpl.class);
        employeeService = new EmployeeServiceImpl(employeeDaoMock);
    }

    @Test
    public void findOneEmployeeByIdNotFound() throws Exception {
        when(employeeDaoMock.findOneEmployeeById(96L)).thenThrow(mock(DataAccessException.class));
        assertThrows(EmployeeIsNotFoundException.class, () -> employeeService.findOneEmployeeById(96L));
    }

    @Test
    public void findOneEmployeeById() throws Exception {
        when(employeeDaoMock.findOneEmployeeById(1L)).thenReturn(testEmployeeInstance);
        Employee foundEmployee = employeeService.findOneEmployeeById(1L);
        assertNotNull(foundEmployee);
        assertEquals(testEmployeeInstance, foundEmployee);
    }

    @Test
    public void saveNewEmployeeNotExisted() throws Exception {
        when(employeeDaoMock.saveNewEmployee(testEmployeeInstance)).thenReturn(testEmployeeInstance);
        when(employeeDaoMock.findEmployeeByAllCredentials(testEmployeeInstance)).thenThrow(mock(DataAccessException.class));
        Employee saved = employeeService.saveNewEmployee(testEmployeeInstance);
        assertNotNull(saved);
        assertEquals(testEmployeeInstance, saved);
    }

    @Test
    public void saveNewEmployeeExisted() throws Exception {
        when(employeeDaoMock.findEmployeeByAllCredentials(testEmployeeInstance)).thenReturn(testEmployeeInstance);
        assertThrows(EmployeeIsAlreadyExisted.class, () -> employeeService.saveNewEmployee(testEmployeeInstance));
    }

    @Test
    public void updateEmployeeNotExisted() throws Exception {
        when(employeeDaoMock.updateEmployee(testEmployeeInstance.getId(), testEmployeeInstance)).thenThrow(mock(DataAccessException.class));
        assertThrows(EmployeeIsNotFoundException.class, () -> employeeService.updateEmployee(testEmployeeInstance.getId(), testEmployeeInstance));
    }

}
