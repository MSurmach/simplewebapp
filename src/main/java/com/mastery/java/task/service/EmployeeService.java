package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.EmployeeIsAlreadyExisted;
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
     * @throws EmployeeIsNotFoundException see {@link EmployeeIsNotFoundException} description.
     */
    Employee findOneEmployeeById(Long id) throws EmployeeIsNotFoundException;

    /**
     * Saves the new {@link Employee} to a common storage.
     *
     * @param employee - the instance of the {@link Employee}, that should be saved.
     * @throws EmployeeIsAlreadyExisted see {@link EmployeeIsAlreadyExisted} description.
     */
    Employee saveNewEmployee(Employee employee) throws EmployeeIsAlreadyExisted;

    /**
     * Updates already existing {@link Employee}
     *
     * @param employee the instance of the {@link Employee} with updated properties.
     * @throws EmployeeIsNotFoundException see {@link EmployeeIsNotFoundException} description.
     */
    void updateEmployee(Employee employee) throws EmployeeIsNotFoundException;

    /**
     * Deletes the {@link Employee} from a common storage.
     *
     * @param id of the {@link Employee} instance, that should be deleted.
     */
    void deleteEmployee(Long id);
}
