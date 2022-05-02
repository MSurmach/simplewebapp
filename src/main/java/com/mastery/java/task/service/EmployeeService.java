package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Optional<Employee> findEmployeeById(Long id);

    List<Employee> findEmployeesByName(String firstName, String lastName);

    Employee saveEmployee(Employee employee);

    Optional<Employee> updateEmployee(Long id, Employee employee);

    boolean deleteEmployee(Long id);
}
