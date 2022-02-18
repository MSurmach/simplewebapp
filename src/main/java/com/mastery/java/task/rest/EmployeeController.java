package com.mastery.java.task.rest;

import ch.qos.logback.classic.Logger;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.SimpleWebAppExceptionResponse;
import com.mastery.java.task.exceptions.MyServiceNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> findEmployeesByName(@RequestParam(required = false) String firstname,
                                              @RequestParam(required = false) String lastname) throws MyServiceNotFoundException {
        LOG.info("IN: firstname = {}, lastname = {}", firstname, lastname);
        if (firstname == null && lastname == null) return findAllEmployees();
        List<Employee> found = employeeService.findEmployeesByName(firstname, lastname);
        LOG.info("OUT: number of found employees = {}", found.size());
        return found;
    }

    private List<Employee> findAllEmployees() throws MyServiceNotFoundException {
        LOG.info("IN: no params");
        List<Employee> allEmployees = employeeService.findAllEmployees();
        LOG.info("OUT: all employees list, with size = {}", allEmployees.size());
        return allEmployees;
    }

    @GetMapping(value = "/{id}")
    public Employee findEmployeeById(@PathVariable @Positive Long id) throws MyServiceNotFoundException {
        LOG.info("IN: id = {}", id);
        Employee found = employeeService.findEmployeeById(id);
        LOG.info("OUT: detected employee = {} ", found);
        return found;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody @Valid Employee employee) {
        LOG.info("IN: employee = {}", employee);
        Employee saved = employeeService.saveEmployee(employee);
        LOG.info("OUT: saved employee = {}", employee);
        return saved;
    }

    @PutMapping(value = "/{id}")
    public Employee updateEmployee(@PathVariable @Positive Long id, @RequestBody Employee employee) throws MyServiceNotFoundException {
        LOG.info("IN: update params -> id = {}, employee  = {}", id, employee);
        Employee updated = employeeService.updateEmployee(id, employee);
        LOG.info("OUT: updated employee = {}", updated);
        return updated;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable @Positive Long id) throws MyServiceNotFoundException {
        LOG.info("IN: delete params -> id = {}", id);
        employeeService.deleteEmployee(id);
        LOG.info("OUT: no content, the deleting is successful");
    }
}
