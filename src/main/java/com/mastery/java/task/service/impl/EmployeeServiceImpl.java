package com.mastery.java.task.service.impl;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.ResourceIsAlreadyExistedException;
import com.mastery.java.task.exceptions.ResourceIsNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    public Employee saveNewEmployee(Employee employee) throws ResourceIsAlreadyExistedException {
        Employee saved;
        try {
            employeeDao.save(employee);
            throw new ResourceIsAlreadyExistedException("The provided employee is already saved");
        } catch (EntityNotFoundException exception) {
            saved = employeeDao.save(employee);
        }
        return saved;
    }

    @Override
    public void updateEmployee(Long id, Employee employee) throws ResourceIsNotFoundException {
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
