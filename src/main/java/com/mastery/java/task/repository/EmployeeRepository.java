package com.mastery.java.task.repository;

import com.mastery.java.task.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByFirstnameContainsAndLastnameContainsAllIgnoreCase(String firstname, String lastname);

    /*@Query("SELECT e FROM Employee e " +
            "WHERE (:firstname IS NULL OR e.firstname LIKE %:firstname%)" +
            "AND (:lastname IS NULL OR e.lastname LIKE %:lastname%)")
    List<Employee> findByFirstnameContainsAndLastnameContains(@Param("firstname") String firstname, @Param("lastname") String lastname);*/
}
