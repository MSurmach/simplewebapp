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
     * @throws {@link EmployeeIsNotFoundException} if any employees don't found with this id.
     */
    Employee findOneEmployeeById(Long id) throws EmployeeIsNotFoundException;

    /**
     * Saves the new {@link Employee} to a common storage.
     *
     * @param employee - the instance of the {@link Employee}, that should be saved.
     * @throws EmployeeHasAlreadyExisted if instance of the {@link Employee} fully equals to already existing.
     */
    void saveNewEmployee(Employee employee) throws EmployeeHasAlreadyExisted;

    /**
     * Updates already existing {@link Employee}
     *
     * @param employee the instance of the {@link Employee} with updated properties.
     * @param id       of the {@link Employee} instance, that should be updated.
     * @throws EmployeeIsNotFoundException if any {@link Employee} instances don't found with this id.
     */
    void updateEmployee(Employee employee, Long id) throws EmployeeIsNotFoundException;

    /**
     * Deletes the {@link Employee} from a common storage.
     *
     * @param id of the {@link Employee} instance, that should be deleted.
     * @throws EmployeeIsNotFoundException if any {@link Employee} don't found with this id.
     */
    void deleteEmployee(Long id) throws EmployeeIsNotFoundException;
}
