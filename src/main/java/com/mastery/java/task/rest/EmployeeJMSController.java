package com.mastery.java.task.rest;

import ch.qos.logback.classic.Logger;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.SimpleWebAppExceptionResponse;
import com.mastery.java.task.jms.EmployeeJmsProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/activemq/employees")
@Tag(name = "EmployeeJMS", description = "Represents the saving employee through the JMS")
public class EmployeeJMSController {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeJmsProducer employeeJmsProducer;

    @Autowired
    public void setEmployeeJmsProducer(EmployeeJmsProducer employeeJmsProducer) {
        this.employeeJmsProducer = employeeJmsProducer;
    }

    @Operation(
            summary = "Saves new employee",
            responses = {
                    @ApiResponse(responseCode = "201", description = "The employee saved successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request. Violating the constraints",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleWebAppExceptionResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleWebAppExceptionResponse.class)))
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(@RequestBody @Valid Employee employee) {
        LOG.info("IN addEmployee: employee = {}", employee);
        employeeJmsProducer.produce(employee);
        LOG.info("OUT addEmployee: saved successfully");
    }
}
