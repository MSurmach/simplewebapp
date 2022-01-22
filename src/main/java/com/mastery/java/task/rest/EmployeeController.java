package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.ResourceIsAlreadyExistedException;
import com.mastery.java.task.exceptions.ResourceIsNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The controller receives HTTP requests and transfers them to the appropriate handler to process requests.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    /**
     * Setter for autowiring dependency.
     *
     * @param employeeService
     */
    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * The handler for getting all available employees from the database.
     *
     * @return {@link ResponseEntity} - response with HTTP code status and a body that will contain a collection of all employees.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> allEmployees() {
        return new ResponseEntity<>(employeeService.allEmployees(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee findOneEmployeeById(@PathVariable Long id) throws ResourceIsNotFoundException {
        return employeeService.findOneEmployeeById(id);
    }

    /**
     * The handler for saving a new employee.
     *
     * @param employee a new {@link Employee} instance that needs to be saved
     * @return {@link ResponseEntity} response with HTTP code status, that will be 200 ("OK") if an employee is saved, or 400 ("BAD_REQUEST"), if the employee is not saved.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> saveNewEmployee(@RequestBody Employee employee) {
        try {
            employeeService.saveNewEmployee(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceIsAlreadyExistedException resourceIsAlreadyExistedException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * The handler for updating selected employee data.
     *
     * @param id       of the {@link Employee} instance, that should be updated.
     * @param employee a new {@link Employee} instance that needs to be updated.
     * @return {@link ResponseEntity} - response with HTTP code status, that will be 200 ("OK") if an employee is updated, or 404 ("NOT_FOUND"), if the employee is not found for the update.
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            employeeService.updateEmployee(id, employee);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceIsNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * The handler for deleting selected employee.
     *
     * @param id of the employee that needs to be deleted.
     * @return {@link ResponseEntity} - response with HTTP code status.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
