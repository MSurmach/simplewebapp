package com.mastery.java.task.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastery.java.task.config.JsonConfiguration;
import com.mastery.java.task.config.MockedDaoConfig;
import com.mastery.java.task.config.WebConfiguration;
import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.service.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JsonConfiguration.class, MockedDaoConfig.class, WebConfiguration.class})
@WebAppConfiguration
public class EmployeeControllerTest {

    private MockMvc mockMvc;
    private final Employee testEmployeeInstance;
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

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void setEmployeeService() {
        EmployeeService employeeService = (EmployeeService) webApplicationContext.getBean(EmployeeService.class);
        Assert.assertNotNull(employeeService);
    }

    @Test
    public void allEmployees() throws Exception {
        when(employeeDaoMock.allEmployees()).thenReturn(Collections.singletonList(testEmployeeInstance));
        mockMvc.perform(get("/employees/all"))
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
        Assert.assertEquals(objectMapper.writeValueAsString(testEmployeeInstance), result.getResponse().getContentAsString());
    }

    @Test
    public void saveNewEmployee() throws Exception {
        when(employeeDaoMock.saveNewEmployee(testEmployeeInstance)).thenReturn(testEmployeeInstance);
        mockMvc.perform(post("/employees/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(testEmployeeInstance))
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateEmployee() throws Exception {
        Employee updatedEmployee = testEmployeeInstance;
        updatedEmployee.setJobTitle("Lead software architect");
        when(employeeDaoMock.updateEmployee(testEmployeeInstance)).thenReturn(updatedEmployee);
        mockMvc.perform(put("/employees")
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
