package com.mastery.java.task.service.impl;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.ResourceIsNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> allEmployees() {
        return employeeDao.findAll();
    }

    @Override
    public Employee findOneEmployeeById(Long id) throws ResourceIsNotFoundException {
        return employeeDao.findById(id).orElseThrow(() -> {
            String msg = String.format("Employee with id = %d is not found", id);
            return new ResourceIsNotFoundException(msg);
        });
    }

    @Override
    public List<Employee> findEmployeeByFirstName(String firstName) {
        return employeeDao.findByFirstName(firstName);
    }

    @Override
    public List<Employee> findEmployeeByLastName(String lastName) {
        return employeeDao.findByLastName(lastName);
    }

    @Override
    public List<Employee> findEmployeeByFirstNameAndLastName(String firstName, String lastName) {
        return employeeDao.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Employee saveNewEmployee(Employee employee) {
        return employeeDao.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) throws ResourceIsNotFoundException {
        Employee toUpdate = findOneEmployeeById(id);
        toUpdate.setFirstName(employee.getFirstName());
        toUpdate.setLastName(employee.getLastName());
        toUpdate.setDepartmentId(employee.getDepartmentId());
        toUpdate.setDateOfBirth(employee.getDateOfBirth());
        toUpdate.setGender(employee.getGender());
        toUpdate.setJobTitle(employee.getJobTitle());
        return employeeDao.save(toUpdate);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeDao.deleteById(id);
    }
}
