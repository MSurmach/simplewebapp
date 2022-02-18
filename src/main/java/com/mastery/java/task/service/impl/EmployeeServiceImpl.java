package com.mastery.java.task.service.impl;

import ch.qos.logback.classic.Logger;
import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.MyServiceNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> findAllEmployees() throws MyServiceNotFoundException {
        List<Employee> found = employeeDao.findAll();
        if (found.isEmpty()) {
            String message = "No one employee is found";
            throw new MyServiceNotFoundException(message);
        }
        return found;
    }

    @Override
    public Employee findEmployeeById(Long id) throws MyServiceNotFoundException {
        return employeeDao.findById(id).orElseThrow(() -> {
            String message = String.format("Employee with {id = %d} is not found", id);
            return new MyServiceNotFoundException(message);
        });
    }

    @Override
    public List<Employee> findEmployeesByName(String firstname, String lastname) throws MyServiceNotFoundException {
        List<Employee> foundEmployees = employeeDao.findByFirstnameContainsAndLastnameContains(firstname, lastname);
        if (foundEmployees.isEmpty()) {
            String message = String.format("Employees with {firstname = %s, lastname = %s} is not found", firstname, lastname);
            throw new MyServiceNotFoundException(message);
        }
        return foundEmployees;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeDao.save(employee);
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
    public void deleteEmployee(Long id) throws MyServiceNotFoundException {
        Employee found = findEmployeeById(id);
        employeeDao.delete(found);
    }
}
