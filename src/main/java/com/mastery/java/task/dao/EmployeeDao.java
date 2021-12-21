package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;

import java.util.List;

public interface EmployeeDao {

    /**
     * Retrieves all available employees from a database.
     *
     * @return the collection of all employees.
     */
    List<Employee> allEmployees();

    /**
     * Finds the one {@link Employee} instance in a database by the provided id.
     *
     * @param id of the requested employee.
     * @return the instance of {@link Employee}, that matches to id.
     */
    Employee findOneEmployeeById(Long id);

    /**
     * @param employee
     * @return
     */
    Employee findEmployeeByAllCredentials(Employee employee);

    /**
     * Saves the new {@link Employee} to a database.
     *
     * @param employee - the instance of the {@link Employee}, that should be saved.
     */
    Employee saveNewEmployee(Employee employee);

    /**
     * Updates already existing {@link Employee} in a database
     *
     * @param employee the instance of the {@link Employee} with updated properties.
     */
    Employee updateEmployee(Employee employee);

    /**
     * Deletes the {@link Employee} from a database.
     *
     * @param id of the {@link Employee} instance, that should be deleted.
     */
    void deleteEmployee(Long id);

}
