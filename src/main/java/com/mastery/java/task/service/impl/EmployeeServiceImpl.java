package com.mastery.java.task.service.impl;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.MyServiceNotFoundException;
import com.mastery.java.task.repository.EmployeeRepository;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Employee with {id = %d} is not found", id);
            return new MyServiceNotFoundException(message);
        });
    }

    @Override
    public List<Employee> findEmployeesByName(String firstname, String lastname) {
        List<Employee> foundEmployees = employeeRepository.findByFirstnameContainsAndLastnameContainsAllIgnoreCase(firstname, lastname);
        if (foundEmployees.isEmpty()) {
            String message = String.format("Employees with {firstname = %s, lastname = %s} is not found", firstname, lastname);
            throw new MyServiceNotFoundException(message);
        }
        return foundEmployees;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee toUpdate = findEmployeeById(id);
        toUpdate.setFirstname(employee.getFirstname());
        toUpdate.setLastname(employee.getLastname());
        toUpdate.setDepartmentId(employee.getDepartmentId());
        toUpdate.setDateOfBirth(employee.getDateOfBirth());
        toUpdate.setGender(employee.getGender());
        toUpdate.setJobTitle(employee.getJobTitle());
        return saveEmployee(toUpdate);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee found = findEmployeeById(id);
        employeeRepository.delete(found);
    }
}
