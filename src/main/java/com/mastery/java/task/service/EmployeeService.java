package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.ResourceIsAlreadyExistedException;
import com.mastery.java.task.exceptions.ResourceIsNotFoundException;

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
     * @throws ResourceIsNotFoundException see {@link ResourceIsNotFoundException} description.
     */
    Employee findOneEmployeeById(Long id) throws ResourceIsNotFoundException;

    /**
     * Saves the new {@link Employee} to a common storage.
     *
     * @param employee - the instance of the {@link Employee}, that should be saved.
     * @throws ResourceIsAlreadyExistedException see {@link ResourceIsAlreadyExistedException} description.
     */
    Employee saveNewEmployee(Employee employee) throws ResourceIsAlreadyExistedException;

    /**
     * Updates already existing {@link Employee}
     *
     * @param id       of the {@link Employee} instance, that should be updated.
     * @param employee the instance of the {@link Employee} with updated properties.
     * @throws ResourceIsNotFoundException see {@link ResourceIsNotFoundException} description.
     */
    void updateEmployee(Long id, Employee employee) throws ResourceIsNotFoundException;

    /**
     * Deletes the {@link Employee} from a common storage.
     *
     * @param id of the {@link Employee} instance, that should be deleted.
     */
    void deleteEmployee(Long id);
}
