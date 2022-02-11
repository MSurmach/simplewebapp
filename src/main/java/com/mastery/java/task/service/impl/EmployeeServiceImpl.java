package com.mastery.java.task.service.impl;

import ch.qos.logback.classic.Logger;
import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.MyServiceIsNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
        LOG.info("The service layer has initialized successfully");
    }

    @Override
    public List<Employee> findAllEmployees() throws MyServiceIsNotFoundException {
        LOG.info("Trying to find all employees...");
        List<Employee> found = employeeDao.findAll();
        if (found.isEmpty()) {
            String message = "No one employee is found";
            LOG.warn("Because: \"{}\", the exception {} will be thrown", message, MyServiceIsNotFoundException.class.getSimpleName());
            throw new MyServiceIsNotFoundException(message);
        }
        LOG.info("All employees are retrieved successfully");
        return found;
    }

    @Override
    public Employee findEmployeeById(Long id) throws MyServiceIsNotFoundException {
        LOG.info("Trying to find an employee with id = {}...", id);
        Employee found = employeeDao.findById(id).orElseThrow(() -> {
            String message = String.format("Employee with {id = %d} is not found", id);
            LOG.warn("Because: \"{}\", the exception {} will be thrown", message, MyServiceIsNotFoundException.class.getSimpleName());
            return new MyServiceIsNotFoundException(message);
        });
        LOG.info("Employee with id = {} is found", id);
        return found;
    }

    @Override
    public List<Employee> findEmployeesByName(String firstname, String lastname) throws MyServiceIsNotFoundException {
        LOG.info("Trying to find employees by their names. Firstname = {}, lastname = {}", firstname, lastname);
        Employee needToFind = new Employee(firstname, lastname);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        List<Employee> found = employeeDao.findAll(Example.of(needToFind, matcher));
        if (found.isEmpty()) {
            String message = String.format("Employees with {firstname = %s, lastname = %s} is not found", firstname, lastname);
            LOG.warn("Because: \"{}\", the exception {} will be thrown", message, MyServiceIsNotFoundException.class.getSimpleName());
            throw new MyServiceIsNotFoundException(message);
        }
        LOG.info("Employee with firstname = {}, lastname = {} is found", firstname, lastname);
        return found;
    }

    @Override
    public Employee saveEmployee(@Valid Employee employee) {
        LOG.info("Trying to persist an employee...");
        Employee saved = employeeDao.save(employee);
        LOG.info("Employee: {} is saved successfully", employee);
        return saved;
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        LOG.info("Trying to update an employee...");
        Employee toUpdate = findEmployeeById(id);
        LOG.info("Employee before update: {}", toUpdate);
        toUpdate.setFirstname(employee.getFirstname());
        toUpdate.setLastname(employee.getLastname());
        toUpdate.setDepartmentId(employee.getDepartmentId());
        toUpdate.setDateOfBirth(employee.getDateOfBirth());
        toUpdate.setGender(employee.getGender());
        toUpdate.setJobTitle(employee.getJobTitle());
        LOG.info("Employee after update: {}", employee);
        return saveEmployee(toUpdate);
    }

    @Override
    public void deleteEmployee(Long id) throws MyServiceIsNotFoundException {
        LOG.info("Trying to delete an employee...");
        findEmployeeById(id);
        employeeDao.deleteById(id);
        LOG.info("Employee with id = {} was deleted", id);
    }
}
