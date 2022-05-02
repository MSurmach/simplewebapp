package com.mastery.java.task.rest;

import ch.qos.logback.classic.Logger;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.SimpleWebAppExceptionResponse;
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
import java.util.Optional;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@RestController
@Validated
@RequestMapping("/employees")
@Tag(name = "Employee", description = "Represents the employee API")
public class EmployeeController {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
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
    public List<Employee> findEmployees(@RequestParam(required = false, defaultValue = "") String firstname,
                                        @RequestParam(required = false, defaultValue = "") String lastname) {
        LOG.info("IN findEmployees: firstname = {}, lastname = {}", firstname, lastname);
        List<Employee> found = employeeService.findEmployeesByName(firstname, lastname);
        if (found.isEmpty()) {
            String message = String.format("Employees with {firstname = %s, lastname = %s} is not found", firstname, lastname);
            throw new MyServiceNotFoundException(message);
        }
        LOG.info("OUT findEmployees: number of found employees = {}", found.size());
        return found;
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
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Success! The employee with provided id",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Employee.class))),
                    @ApiResponse(
                            responseCode = "400", description = "Bad request. Violating the constraints",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = SimpleWebAppExceptionResponse.class)))),
                    @ApiResponse(
                            responseCode = "404", description = "Employee not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleWebAppExceptionResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleWebAppExceptionResponse.class)))
            })

    @GetMapping(value = "/{id}")
    public Employee findEmployeeById(@PathVariable @Positive Long id) {
        LOG.info("IN findEmployeeById: id = {}", id);
        Employee found = employeeService.findEmployeeById(id).orElseThrow(() -> {
            String message = String.format("Employee with {id = %d} is not found", id);
            return new MyServiceNotFoundException(message);
        });
        LOG.info("OUT findEmployeeById: detected employee = {} ", found);
        return found;
    }

    @Operation(
            summary = "Saves new employee",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "The saved employee",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Employee.class))),
                    @ApiResponse(
                            responseCode = "400", description = "Bad request. Violating the constraints",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = SimpleWebAppExceptionResponse.class)))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleWebAppExceptionResponse.class)))
            })

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody @Valid Employee employee) {
        LOG.info("IN saveEmployee: employee = {}", employee);
        Employee saved = employeeService.saveEmployee(employee);
        LOG.info("OUT saveEmployee: saved employee = {}", saved);
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
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Success! The updated employee",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Employee.class))),
                    @ApiResponse(
                            responseCode = "400", description = "Bad request. Violating the constraints",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = SimpleWebAppExceptionResponse.class)))),
                    @ApiResponse(
                            responseCode = "404", description = "Employee not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleWebAppExceptionResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleWebAppExceptionResponse.class)))
            })

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee updateEmployee(@PathVariable @Positive Long id, @RequestBody @Valid Employee employee) {
        LOG.info("IN updateEmployee: update params -> id = {}, employee  = {}", id, employee);
        Employee updated = employeeService.updateEmployee(id, employee).orElseThrow(() -> {
            String message = String.format("Employee with {id = %d} is not updated, because it's not found", id);
            return new MyServiceNotFoundException(message);
        });
        LOG.info("OUT updateEmployee: updated employee = {}", updated);
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
            responses = {
                    @ApiResponse(responseCode = "204", description = "The employee is deleted"),
                    @ApiResponse(
                            responseCode = "400", description = "Bad request. Violating the constraints",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleWebAppExceptionResponse.class))),
                    @ApiResponse(
                            responseCode = "404", description = "Employee not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleWebAppExceptionResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleWebAppExceptionResponse.class)))
            }
    )

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable @Positive Long id) {
        LOG.info("IN deleteEmployee: delete params -> id = {}", id);
        if (!employeeService.deleteEmployee(id)) {
            String message = String.format("Employee with {id = %d} is not deleted, because it's not found", id);
            throw new MyServiceNotFoundException(message);
        }
        LOG.info("OUT deleteEmployee: no content, the deleting is successful");
    }
}
