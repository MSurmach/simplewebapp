package com.mastery.java.task.service.impl;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.EmployeeIsAlreadyExisted;
import com.mastery.java.task.exceptions.EmployeeIsNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;

    @Autowired
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> allEmployees() {
        return employeeDao.allEmployees();
    }

    @Override
    public Employee findOneEmployeeById(Long id) throws EmployeeIsNotFoundException {
        try {
            return employeeDao.findOneEmployeeById(id);
        } catch (DataAccessException exception) {
            throw new EmployeeIsNotFoundException("The employee is not found");
        }
    }

    @Override
    public void saveNewEmployee(Employee employee) throws EmployeeIsAlreadyExisted {
        try {
            employeeDao.findEmployeeByAllCredentials(employee);
            throw new EmployeeIsAlreadyExisted("The provided employee is already saved");
        } catch (DataAccessException exception) {
            employeeDao.saveNewEmployee(employee);
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeDao.updateEmployee(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeDao.deleteEmployee(id);
    }
}
