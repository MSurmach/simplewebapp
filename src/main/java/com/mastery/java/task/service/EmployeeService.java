package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.MyServiceIsNotFoundException;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAllEmployees() throws MyServiceIsNotFoundException;

    Employee findEmployeeById(Long id) throws MyServiceIsNotFoundException;

    List<Employee> findEmployeesByName(String firstName, String lastName) throws MyServiceIsNotFoundException;

    Employee saveEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id) throws MyServiceIsNotFoundException;
}
