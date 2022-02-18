package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.MyServiceNotFoundException;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAllEmployees() throws MyServiceNotFoundException;

    Employee findEmployeeById(Long id) throws MyServiceNotFoundException;

    List<Employee> findEmployeesByName(String firstName, String lastName) throws MyServiceNotFoundException;

    Employee saveEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id) throws MyServiceNotFoundException;
}
