package com.mastery.java.task.service.impl;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.repository.EmployeeRepository;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> findEmployeesByName(String firstname, String lastname) {
        return employeeRepository.findByFirstnameContainsAndLastnameContainsAllIgnoreCase(firstname, lastname);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> updateEmployee(Long id, Employee employee) {
        Optional<Employee> toUpdateOptional = findEmployeeById(id);
        if (toUpdateOptional.isPresent()) {
            Employee toUpdate = toUpdateOptional.get();
            toUpdate.setFirstname(employee.getFirstname());
            toUpdate.setLastname(employee.getLastname());
            toUpdate.setDepartmentId(employee.getDepartmentId());
            toUpdate.setDateOfBirth(employee.getDateOfBirth());
            toUpdate.setGender(employee.getGender());
            toUpdate.setJobTitle(employee.getJobTitle());
            return Optional.of(saveEmployee(toUpdate));
        }
        return toUpdateOptional;
    }

    @Override
    public boolean deleteEmployee(Long id) {
        Optional<Employee> toDeleteEmployee = findEmployeeById(id);
        if (toDeleteEmployee.isPresent()) {
            employeeRepository.delete(toDeleteEmployee.get());
            return true;
        }
        return false;
    }
}
