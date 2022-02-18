package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.lang.Nullable;

import java.util.List;

public interface EmployeeDao extends JpaRepository<Employee, Long> {
    //List<Employee> findByFirstnameContainsAndLastnameContains(@Nullable String firstname, @Nullable String lastname);

    @Query("SELECT e FROM Employee e " +
            "WHERE (:firstname IS NULL OR e.firstname LIKE %:firstname%)" +
            "AND (:lastname IS NULL OR e.lastname LIKE %:lastname%)")
    List<Employee> findByFirstnameContainsAndLastnameContains(@Param("firstname") String firstname, @Param("lastname") String lastname);
}
