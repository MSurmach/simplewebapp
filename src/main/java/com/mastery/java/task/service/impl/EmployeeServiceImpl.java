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
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> allEmployees() {
        return employeeDao.findAll();
    }

    @Override
    public Employee findOneEmployeeById(Long id) throws EmployeeIsNotFoundException {
        try {
            return employeeDao.findById(id).get();
        } catch (DataAccessException exception) {
            throw new EmployeeIsNotFoundException("The employee is not found");
        }
    }

    @Override
    public Employee saveNewEmployee(Employee employee) throws EmployeeIsAlreadyExisted {
        Employee saved;
        try {
            employeeDao.save(employee);
            throw new EmployeeIsAlreadyExisted("The provided employee is already saved");
        } catch (DataAccessException exception) {
            saved = employeeDao.save(employee);
        }
        return saved;
    }

    @Override
    public void updateEmployee(Long id, Employee employee) throws EmployeeIsNotFoundException {
        Employee toUpdate = findOneEmployeeById(id);
        toUpdate.setFirstName(employee.getFirstName());
        toUpdate.setLastName(employee.getLastName());
        toUpdate.setDepartmentId(employee.getDepartmentId());
        toUpdate.setDateOfBirth(employee.getDateOfBirth());
        toUpdate.setGender(employee.getGender());
        toUpdate.setJobTitle(employee.getJobTitle());
        employeeDao.save(toUpdate);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeDao.deleteById(id);
    }
}
