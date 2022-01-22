package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.ResourceIsNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> allEmployees() {
        return employeeService.allEmployees();
    }

    @GetMapping(params = {"firstName"})
    public List<Employee> findEmployeeByFirstName(@RequestParam String firstName) {
        return employeeService.findEmployeeByFirstName(firstName);
    }

    @GetMapping(params = {"lastName"})
    public List<Employee> findEmployeeByLastName(@RequestParam String lastName) {
        return employeeService.findEmployeeByLastName(lastName);
    }

    @GetMapping(params = {"firstName", "lastName"})
    public List<Employee> findEmployeeByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        return employeeService.findEmployeeByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping(value = "/{id}")
    public Employee findOneEmployeeById(@PathVariable Long id) throws ResourceIsNotFoundException {
        return employeeService.findOneEmployeeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveNewEmployee(@RequestBody Employee employee) {
        return employeeService.saveNewEmployee(employee);
    }

    @PutMapping(value = "/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) throws ResourceIsNotFoundException {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
