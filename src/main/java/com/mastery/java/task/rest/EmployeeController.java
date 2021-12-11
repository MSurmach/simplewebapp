package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.EmployeeIsNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The controller receives HTTP requests and transfers them to the appropriate handler to process requests.
 */
@Controller
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * The handler for getting all available employees from the database.
     *
     * @return {@link ResponseEntity} - response with HTTP code status and a body that will contain a collection of all employees.
     */
    @GetMapping(value = "/employees/all")
    public ResponseEntity<List<Employee>> allEmployers() {
        return new ResponseEntity<>(employeeService.allEmployees(), HttpStatus.OK);
    }

    /**
     * The handler for getting only one employee by his id.
     *
     * @param id of the requested employee.
     * @return {@link ResponseEntity} - response with HTTP code status and a body that will contain an instance of the requested employee. In case the employee is not found, the response will contain an empty body.
     */
    @GetMapping(value = "/employees/{id}")
    public ResponseEntity<Employee> findOneEmployeeById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(employeeService.findOneEmployeeById(id), HttpStatus.OK);
        } catch (EmployeeIsNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * The handler for updating selected employee data.
     *
     * @param id of the employee that needs to be updated.
     * @return {@link ResponseEntity} - response with HTTP code status.
     */
    @PutMapping(value = "/employees/{id}")
    public ResponseEntity<HttpStatus> updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {
        try {
            employeeService.updateEmployee(employee, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmployeeIsNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * The handler for deleting selected employee.
     *
     * @param id of the employee that needs to be deleted.
     * @return {@link ResponseEntity} - response with HTTP code status.
     */
    @DeleteMapping(value = "/employees/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmployeeIsNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
