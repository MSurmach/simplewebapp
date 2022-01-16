package com.mastery.java.task.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EmployeeControllerTest {

    private final Employee testEmployeeInstance;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private EmployeeDao employeeDaoMock;
    @Autowired
    private ObjectMapper objectMapper;

    {
        testEmployeeInstance = new Employee(
                "John",
                "Peterson",
                Gender.MALE,
                2L,
                "Software architect",
                LocalDate.of(1991, 5, 9));
        testEmployeeInstance.setId(1L);
    }

    @BeforeAll
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void setEmployeeService() {
        EmployeeService employeeService = (EmployeeService) webApplicationContext.getBean(EmployeeService.class);
        assertNotNull(employeeService);
    }

    @Test
    public void allEmployees() throws Exception {
        when(employeeDaoMock.allEmployees()).thenReturn(Collections.singletonList(testEmployeeInstance));
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());
    }

    @Test
    public void findOneEmployeeById() throws Exception {
        Long testId = 1L;
        when(employeeDaoMock.findOneEmployeeById(testId)).thenReturn(testEmployeeInstance);
        MvcResult result = mockMvc.perform(get("/employees/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andReturn();
        assertEquals(objectMapper.writeValueAsString(testEmployeeInstance), result.getResponse().getContentAsString());
    }

    @Test
    public void saveNewEmployee() throws Exception {
        when(employeeDaoMock.saveNewEmployee(testEmployeeInstance)).thenReturn(testEmployeeInstance);
        when(employeeDaoMock.findEmployeeByAllCredentials(testEmployeeInstance)).thenThrow(mock(DataAccessException.class));
        mockMvc.perform(post("/employees/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(testEmployeeInstance))
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateEmployee() throws Exception {
        Long testId = testEmployeeInstance.getId();
        Employee updatedEmployee = testEmployeeInstance;
        updatedEmployee.setJobTitle("Lead software architect");
        when(employeeDaoMock.updateEmployee(testId, testEmployeeInstance)).thenReturn(updatedEmployee);
        mockMvc.perform(put("/employees/{testId}", testId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(testEmployeeInstance))
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteEmployee() throws Exception {
        Long testId = 1L;
        mockMvc.perform(delete("/employees/{id}", testId))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
