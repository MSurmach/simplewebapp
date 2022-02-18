package com.mastery.java.task.rest;

import ch.qos.logback.classic.Logger;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.MyServiceNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@RestController
@Validated
@RequestMapping("/employees")
@Tag(name = "Employee", description = "Represents the employee API")
public class EmployeeController {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "Gets list of employees by their names or all employees in the database (if their names are not provided",
            parameters = {
                    @Parameter(
                            description = "The firstname of the employee that needs to be fetched",
                            name = "firstname",
                            in = QUERY
                    ),
                    @Parameter(
                            description = "The lastname of the employee that needs to be fetched",
                            name = "lastname",
                            in = QUERY
                    ),
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Success! The employee with provided id",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Employee.class)))))

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

    @Operation(
            summary = "Gets employee by id",
            parameters = {
                    @Parameter(
                            description = "The id of the employee that needs to be fetched",
                            required = true,
                            name = "id",
                            in = PATH
                    )
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Success! The employee with provided id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))))

    @GetMapping(value = "/{id}")
    public Employee findEmployeeById(@PathVariable @Positive Long id) throws MyServiceNotFoundException {
        LOG.info("IN: id = {}", id);
        Employee found = employeeService.findEmployeeById(id);
        LOG.info("OUT: detected employee = {} ", found);
        return found;
    }

    @Operation(
            summary = "Saves new employee",
            responses = @ApiResponse(
                    responseCode = "201",
                    description = "The saved employee",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))))

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody @Valid Employee employee) {
        LOG.info("IN: employee = {}", employee);
        Employee saved = employeeService.saveEmployee(employee);
        LOG.info("OUT: saved employee = {}", employee);
        return saved;
    }

    @Operation(
            summary = "Updates an existed employee",
            parameters = {
                    @Parameter(
                            description = "The id of the employee that needs to be fetched",
                            name = "id",
                            in = PATH
                    )
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Success! The updated employee",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))))

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee updateEmployee(@PathVariable @Positive Long id, @RequestBody @Valid Employee employee) throws MyServiceNotFoundException {
        LOG.info("IN: update params -> id = {}, employee  = {}", id, employee);
        Employee updated = employeeService.updateEmployee(id, employee);
        LOG.info("OUT: updated employee = {}", updated);
        return updated;
    }

    @Operation(
            summary = "Deletes the employee by provided id",
            parameters = {
                    @Parameter(
                            description = "The id of the employee that needs to be deleted",
                            required = true,
                            name = "id",
                            in = PATH
                    )
            },
            responses = @ApiResponse(
                    responseCode = "204",
                    description = "The employee is deleted"))

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable @Positive Long id) throws MyServiceNotFoundException {
        LOG.info("IN: delete params -> id = {}", id);
        employeeService.deleteEmployee(id);
        LOG.info("OUT: no content, the deleting is successful");
    }
}
