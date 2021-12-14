package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.EmployeeHasAlreadyExisted;
import com.mastery.java.task.exceptions.EmployeeIsNotFoundException;

import java.util.List;

public interface EmployeeService {

    /**
     * Retrieves all available employees.
     *
     * @return the collection of all employees.
     */
    List<Employee> allEmployees();

    /**
     * Finds the one {@link Employee} by the provided id.
     *
     * @param id of the requested employee.
     * @return the instance of {@link Employee}, that matches to id.
     */
    Employee findOneEmployeeById(Long id);

    /**
     * Saves the new {@link Employee} to a common storage.
     *
     * @param employee - the instance of the {@link Employee}, that should be saved.
     */
    void saveNewEmployee(Employee employee);

    /**
     * Updates already existing {@link Employee}
     *
     * @param employee the instance of the {@link Employee} with updated properties.
     */
    void updateEmployee(Employee employee);

    /**
     * Deletes the {@link Employee} from a common storage.
     *
     * @param id of the {@link Employee} instance, that should be deleted.
     */
    void deleteEmployee(Long id);
}
