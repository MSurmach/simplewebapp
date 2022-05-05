package com.mastery.java.task.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmployeeController employeeController;
    @MockBean
    private EmployeeService employeeService;
    private static final String EMPTY_STRING = "";
    private static final String COMMON_REQUEST_MAPPING = "/employees";
    private static final Employee TEST_EMPLOYEE_VALID;
    private static final Employee TEST_EMPLOYEE_NOT_VALID;

    static {
        TEST_EMPLOYEE_VALID = new Employee(
                "Maksim",
                "Surmach",
                Gender.MALE,
                7L,
                "Jr. Software Engineer",
                LocalDate.of(1994, 8, 17));
        TEST_EMPLOYEE_VALID.setId(1L);
    }

    static {
        TEST_EMPLOYEE_NOT_VALID = new Employee(TEST_EMPLOYEE_VALID);
        TEST_EMPLOYEE_NOT_VALID.setDateOfBirth(LocalDate.of(2014, 8, 17));
        TEST_EMPLOYEE_NOT_VALID.setId(null);
        TEST_EMPLOYEE_NOT_VALID.setGender(null);
        TEST_EMPLOYEE_NOT_VALID.setFirstname(null);
    }

    @Test
    void findEmployees_ifNoRequestParams_shouldReturn404AndExceptionResponse() throws Exception {
        when(employeeService.findEmployeesByName(EMPTY_STRING, EMPTY_STRING)).
                thenReturn(Collections.emptyList());
        MvcResult result = mockMvc.perform(get("/employees").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String exceptionMessage = String.format("Employees with {firstname = %s, lastname = %s} is not found", EMPTY_STRING, EMPTY_STRING);
        String expectedResponseBody = getExceptionResponseBodyAsString(HttpStatus.NOT_FOUND, exceptionMessage);
        String actualResponseBody = getResponseBodyAsString(result);
        assertResponseBodyAsStringEquality(expectedResponseBody, actualResponseBody);
    }

    @Test
    void findEmployees_withFirstNameAndLastName_shouldReturn200() throws Exception {
        final String testFirstName = "Maksim";
        final String testLastName = "Surmach";
        List<Employee> employeeList = Collections.singletonList(TEST_EMPLOYEE_VALID);
        when(employeeService.findEmployeesByName(testFirstName, testLastName)).
                thenReturn(employeeList);
        MvcResult mvcResult = mockMvc.perform(get("/employees").
                        param("firstname", testFirstName).
                        param("lastname", testLastName)).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        String actualResponseBody = getResponseBodyAsString(mvcResult);
        String expectedResponseBody = objectMapper.writeValueAsString(employeeList);
        System.out.println(expectedResponseBody);
        assertResponseBodyAsStringEquality(expectedResponseBody, actualResponseBody);
    }

    @Test
    void findEmployeeById_whenEmployeeExists_shouldReturn200() throws Exception {
        final Long testId = 1L;
        when(employeeService.findEmployeeById(testId)).
                thenReturn(Optional.of(TEST_EMPLOYEE_VALID));
        MvcResult mvcResult = mockMvc.perform(get("/employees/{id}", testId.toString())).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        String actualResponseBody = getResponseBodyAsString(mvcResult);
        String expectedResponseBody = objectMapper.writeValueAsString(TEST_EMPLOYEE_VALID);
        assertResponseBodyAsStringEquality(expectedResponseBody, actualResponseBody);
    }

    @Test
    void findEmployeeById_whenNotExist_shouldReturn404AndExceptionResponse() throws Exception {
        final Long testId = 100L;
        when(employeeService.findEmployeeById(testId)).
                thenReturn(Optional.empty());
        MvcResult mvcResult = mockMvc.perform(get("/employees/{id}", testId.toString())).
                andExpect(status().isNotFound()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        String exceptionMessage = String.format("Employee with {id = %d} is not found", testId);
        String expectedResponseBody = getExceptionResponseBodyAsString(HttpStatus.NOT_FOUND, exceptionMessage);
        String actualResponseBody = getResponseBodyAsString(mvcResult);
        assertResponseBodyAsStringEquality(expectedResponseBody, actualResponseBody);
    }

    @Test
    void updateEmployee_whenIdNegativeAndEmployeeNotValid_shouldReturn400AndExceptionResponse() throws Exception {
        final Long testIdNegative = -1L;
        Employee updatedNotValid = new Employee(TEST_EMPLOYEE_NOT_VALID);
        updatedNotValid.setId(testIdNegative);
        updatedNotValid.setFirstname("Alex");
        mockMvc.perform(put("/employees/{id}", testIdNegative.toString()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(updatedNotValid))).
                andExpect(status().isBadRequest());
        assertThrows(ConstraintViolationException.class, () -> employeeController.updateEmployee(testIdNegative, updatedNotValid));
    }

    @Test
    void updateEmployee_whenNotFoundToUpdate_shouldReturn400AndExceptionResponse() throws Exception {
        final Long testId = 100L;
        Employee updatedValid = new Employee(TEST_EMPLOYEE_VALID);
        updatedValid.setFirstname("Alex");
        MvcResult mvcResult = mockMvc.perform(put("/employees/{id}", testId.toString()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(updatedValid))).
                andExpect(status().isNotFound()).andReturn();
        assertThrows(MyServiceNotFoundException.class, () -> employeeController.updateEmployee(testId, updatedValid));
        String actualResponseBody = getResponseBodyAsString(mvcResult);
        String expectedExceptionMessage = String.format("Employee with {id = %d} is not updated, because it's not found", testId);
        String expectedResponseBody = getExceptionResponseBodyAsString(HttpStatus.NOT_FOUND, expectedExceptionMessage);
        assertResponseBodyAsStringEquality(expectedResponseBody, actualResponseBody);
    }

    @Test
    void updateEmployee_whenIsValid_shouldReturn200() throws Exception {
        final Long testId = 1L;
        Employee updatedValid = new Employee(TEST_EMPLOYEE_VALID);
        updatedValid.setFirstname("Alex");
        when(employeeService.updateEmployee(testId, updatedValid)).
                thenReturn(Optional.of(updatedValid));
        MvcResult mvcResult = mockMvc.perform(put("/employees/{id}", testId.toString()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(updatedValid))).
                andExpect(status().isOk()).andReturn();
        String actualResponseBody = getResponseBodyAsString(mvcResult);
        String expectedResponseBody = objectMapper.writeValueAsString(updatedValid);
        assertResponseBodyAsStringEquality(expectedResponseBody, actualResponseBody);
    }

    @Test
    void saveEmployee_whenIsValid_shouldReturn201() throws Exception {
        Employee expectedEmployee = new Employee(TEST_EMPLOYEE_VALID);
        expectedEmployee.setId(1L);
        when(employeeService.saveEmployee(TEST_EMPLOYEE_VALID)).
                thenReturn(expectedEmployee);
        MvcResult mvcResult = mockMvc.perform(post(COMMON_REQUEST_MAPPING).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(TEST_EMPLOYEE_VALID))).
                andExpect(status().isCreated()).andReturn();
        String actualResponseBody = getResponseBodyAsString(mvcResult);
        String expectedResponseBody = objectMapper.writeValueAsString(expectedEmployee);
        assertResponseBodyAsStringEquality(expectedResponseBody, actualResponseBody);
    }

    @Test
    void saveEmployee_whenIsNotValid_shouldReturn400AndExceptionResponse() throws Exception {
        mockMvc.perform(post(COMMON_REQUEST_MAPPING).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(TEST_EMPLOYEE_NOT_VALID))).
                andExpect(status().isBadRequest());
        assertThrows(ConstraintViolationException.class, () -> employeeController.saveEmployee(TEST_EMPLOYEE_NOT_VALID));
    }

    @Test
    void deleteEmployee_whenIdIsNegativeOrZero_shouldReturn400AndExceptionResponse() throws Exception {
        final Long testIdNegative = -2L;
        final Long testIdZero = 0L;
        mockMvc.perform(delete(COMMON_REQUEST_MAPPING + "/{id}", testIdNegative.toString()))
                .andExpect(status().isBadRequest());
        assertThrows(ConstraintViolationException.class, () -> employeeController.deleteEmployee(testIdNegative));
        mockMvc.perform(delete(COMMON_REQUEST_MAPPING + "/{id}", testIdZero.toString())).andExpect(status().isBadRequest());
        assertThrows(ConstraintViolationException.class, () -> employeeController.deleteEmployee(testIdZero));
    }

    @Test
    void deleteEmployee_whenIdNotFound_shouldReturn404AndExceptionResponse() throws Exception {
        final Long testAbsentId = 100L;
        MvcResult result = mockMvc.perform(delete(COMMON_REQUEST_MAPPING + "/{id}", testAbsentId.toString()))
                .andExpect(status().isNotFound()).andReturn();
        assertThrows(MyServiceNotFoundException.class, () -> employeeController.deleteEmployee(testAbsentId));
        String expectedExceptionMessage = String.format("Employee with {id = %d} is not deleted, because it's not found", testAbsentId);
        String expectedResponseBody = getExceptionResponseBodyAsString(HttpStatus.NOT_FOUND, expectedExceptionMessage);
        String actualResponseBody = getResponseBodyAsString(result);
        assertResponseBodyAsStringEquality(expectedResponseBody, actualResponseBody);
    }

    @Test
    void deleteEmployee_withValidId_shouldReturn204() throws Exception {
        final Long testId = 1L;
        when(employeeService.deleteEmployee(testId)).thenReturn(true);
        mockMvc.perform(delete(COMMON_REQUEST_MAPPING + "/{id}", testId.toString()))
                .andExpect(status().isNoContent());
    }

    private String getResponseBodyAsString(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    private String getExceptionResponseBodyAsString(HttpStatus httpStatus, String exceptionMessage) throws JsonProcessingException {
        SimpleWebAppExceptionResponse exceptionResponse = new SimpleWebAppExceptionResponse(httpStatus, exceptionMessage);
        return objectMapper.writeValueAsString(exceptionResponse);
    }

    private void assertResponseBodyAsStringEquality(String expected, String actual) {
        String warningMessage = "The content isn't equal to each other";
        assertEquals(warningMessage, expected, actual);
    }
}