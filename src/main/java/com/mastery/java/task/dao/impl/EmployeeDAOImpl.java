package com.mastery.java.task.dao.impl;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.time.LocalDate;
import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Employee> employeeMapper = (rs, rowNum) -> {
        Employee employee = new Employee();
        employee.setId(rs.getLong("employee_id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setDepartmentId(rs.getLong("department_id"));
        employee.setJobTitle(rs.getString("job_title"));
        employee.setGender(Gender.valueOf(rs.getString("gender")));
        employee.setDateOfBirth(rs.getObject("date_of_birth", LocalDate.class));
        return employee;
    };

    @Autowired
    public EmployeeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public List<Employee> allEmployees() {
        String sqlQuery = "SELECT * FROM employee";
        return jdbcTemplate.query(sqlQuery, employeeMapper);
    }

    @Override
    public Employee findOneEmployeeById(Long id) {
        String sqlQuery = "SELECT * FROM employee WHERE employee_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, employeeMapper, id);
    }

    @Override
    public Employee saveNewEmployee(Employee employee) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(employee);
        String sqlQuery = "INSERT INTO employee (first_name, last_name, department_id, job_title, gender, date_of_birth) " +
                "VALUES (:firstName, :lastName, :departmentId, :jobTitle, :gender, :dateOfBirth)";
        namedParameterJdbcTemplate.update(sqlQuery, getNamedMapSource(employee));
        return employee;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        String sqlQuery = String.format("UPDATE employee SET " +
                "first_name = :firstName," +
                "last_name = :lastName," +
                "department_id = :departmentId," +
                "job_title = :jobTitle," +
                "gender = :gender," +
                "date_of_birth = :dateOfBirth" +
                " WHERE employee_id = %s", employee.getId());
        namedParameterJdbcTemplate.update(sqlQuery, getNamedMapSource(employee));
        return employee;
    }

    @Override
    public void deleteEmployee(Long id) {
        String sqlQuery = "DELETE FROM employee WHERE employee_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    private MapSqlParameterSource getNamedMapSource(Employee employee) {
        MapSqlParameterSource namedMapSource = new MapSqlParameterSource();
        namedMapSource
                .addValue("firstName", employee.getFirstName())
                .addValue("lastName", employee.getLastName())
                .addValue("departmentId", employee.getDepartmentId())
                .addValue("jobTitle", employee.getJobTitle())
                .addValue("gender", employee.getGender(), Types.VARCHAR)
                .addValue("dateOfBirth", employee.getDateOfBirth(), Types.DATE);
        return namedMapSource;
    }
}
