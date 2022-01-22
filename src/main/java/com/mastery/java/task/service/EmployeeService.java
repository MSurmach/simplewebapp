package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.ResourceIsAlreadyExistedException;
import com.mastery.java.task.exceptions.ResourceIsNotFoundException;

import java.util.List;

public interface EmployeeService {

    List<Employee> allEmployees();

    Employee findOneEmployeeById(Long id) throws ResourceIsNotFoundException;

    List<Employee> findEmployeeByFirstName(String firstName);

    List<Employee> findEmployeeByLastName(String lastName);

    List<Employee> findEmployeeByFirstNameAndLastName(String firstName, String lastName);

    Employee saveNewEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee employee) throws ResourceIsNotFoundException;

    void deleteEmployee(Long id);
}
