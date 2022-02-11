package com.mastery.java.task.rest;

import ch.qos.logback.classic.Logger;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.MyServiceIsNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
        LOG.info("The controller layer has initialized successfully");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findEmployeesByName(@RequestParam Map<String, String> allParams) throws MyServiceIsNotFoundException {
        if (allParams.isEmpty()) return findAllEmployees();
        LOG.info("The request was received to retrieve all employees with according firstname and lastname");
        return employeeService.findEmployeesByName(allParams.get("firstname"), allParams.get("lastname"));
    }

    private List<Employee> findAllEmployees() throws MyServiceIsNotFoundException {
        LOG.info("The request was received to retrieve all employees");
        return employeeService.findAllEmployees();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee findEmployeeById(@PathVariable Long id) throws MyServiceIsNotFoundException {
        LOG.info("The request was received to retrieve an employee with id = {}", id);
        return employeeService.findEmployeeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody Employee employee) {
        LOG.info("The request was received to save a new employee: {}", employee);
        return employeeService.saveEmployee(employee);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) throws MyServiceIsNotFoundException {
        LOG.info("The request was received to update an employee with id = {}", id);
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) throws MyServiceIsNotFoundException {
        LOG.info("Request was received to delete an employee with id = {}", id);
        employeeService.deleteEmployee(id);
    }
}
