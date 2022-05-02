package com.mastery.java.task.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.dto.SimpleWebAppExceptionResponse;
import com.mastery.java.task.exceptions.MyServiceNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    private static final String EMPTY_STRING = "";
    private static final String COMMON_REQUEST_MAPPING = "/employees";
    private final Employee testEmployeeInstance;

    {
        testEmployeeInstance = new Employee(
                "Maksim",
                "Surmach",
                Gender.MALE,
                7L,
                "Jr. Software Engineer",
                LocalDate.of(1994, 8, 17));
        testEmployeeInstance.setId(1L);
    }

    @Test
    void findEmployees_ifNoParams_shouldReturn404AndExceptionResponse() throws Exception {
        when(employeeService.findEmployeesByName(EMPTY_STRING, EMPTY_STRING)).thenReturn(Collections.emptyList());
        String exceptionMessage = String.format("Employees with {firstname = %s, lastname = %s} is not found", EMPTY_STRING, EMPTY_STRING);
        SimpleWebAppExceptionResponse exceptionResponse = new SimpleWebAppExceptionResponse(HttpStatus.NOT_FOUND, new MyServiceNotFoundException(exceptionMessage));
        MvcResult result = mockMvc.perform(get("/employees").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        verify(employeeService).findEmployeesByName(EMPTY_STRING, EMPTY_STRING);
        String expectedResponseBody = objectMapper.writeValueAsString(exceptionResponse);
        String actualResponseBody = result.getResponse().getContentAsString();
        assertTrue("The response is not correct", actualResponseBody.equalsIgnoreCase(expectedResponseBody));
    }

    @Test
    void findEmployees_withFirstNameAndLastName() throws Exception {
        final String testFirstName = "Maksim";
        final String testLastName = "Surmach";
        when(employeeService.findEmployeesByName(testFirstName, testLastName)).thenReturn(Collections.singletonList(testEmployeeInstance));
        MvcResult mvcResult = mockMvc.perform(get("/employees").
                        param("firstname", testFirstName).
                        param("lastname", testLastName)).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        verify(employeeService).findEmployeesByName(testFirstName, testLastName);
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class);
        List<Employee> found = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), javaType);
        Employee expectedEmployee = found.get(0);
        assertTrue("The response is not correct", expectedEmployee.equals(testEmployeeInstance));
    }

    @Test
    void findEmployeeById_whenEmployeeExists() throws Exception {
        final Long testId = 1L;
        when(employeeService.findEmployeeById(testId)).thenReturn(Optional.of(testEmployeeInstance));
        MvcResult mvcResult = mockMvc.perform(get("/employees/{id}", testId.toString())).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        verify(employeeService).findEmployeeById(testId);
        Employee expectedEmployeeInstance = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class);
        assertEquals("Response body is not correct", expectedEmployeeInstance, testEmployeeInstance);
    }

    @Test
    void findEmployeeById_whenNotExistShouldReturn404AndExceptionResponse() throws Exception {
        final Long testId = 100L;
        when(employeeService.findEmployeeById(testId)).thenReturn(Optional.empty());
        String exceptionMessage = String.format("Employee with {id = %d} is not found", testId);
        SimpleWebAppExceptionResponse expectedException = new SimpleWebAppExceptionResponse(HttpStatus.NOT_FOUND, new MyServiceNotFoundException(exceptionMessage));
        MvcResult mvcResult = mockMvc.perform(get("/employees/{id}", testId.toString())).
                andExpect(status().isNotFound()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        verify(employeeService).findEmployeeById(testId);
        String expectedResponseBody = objectMapper.writeValueAsString(expectedException);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertTrue("The response is not correct", expectedResponseBody.equalsIgnoreCase(actualResponseBody));
    }

    /*@Test
    void updateEmployee_whenIdNegativeShouldReturn400AndExceptionResponse() throws Exception {
        final Long testId = -1L;
        mockMvc.perform(put("/employees/{id}", testId.toString())).
                andExpect(status().isBadRequest());
    }*/

    @Test
    void saveEmployee_whenEmployeeIsValid() throws Exception {
        when(employeeService.saveEmployee(testEmployeeInstance)).thenReturn(testEmployeeInstance);
        mockMvc.perform(post(COMMON_REQUEST_MAPPING).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(testEmployeeInstance))).
                andExpect(status().isCreated());
    }

    @Test
    void saveEmployee_whenEmployeeIsNotValid() throws Exception {
        Employee notValidEmployee = new Employee(testEmployeeInstance);
        notValidEmployee.setDateOfBirth(LocalDate.of(2014, 8, 17));
        notValidEmployee.setId(null);
        mockMvc.perform(post(COMMON_REQUEST_MAPPING).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(notValidEmployee))).
                andExpect(status().isBadRequest());
    }

    @Test
    void deleteEmployee() {
    }
}