package com.mastery.java.task.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Simple blueprint for creating Employee instances, which later on will be added into a database.
 */
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Long departmentId;
    private String jobTitle;
    private LocalDate dateOfBirth;

    public Employee() {
    }

    public Employee(String firstName, String lastName, Gender gender, Long departmentId, String jobTitle, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.departmentId = departmentId;
        this.jobTitle = jobTitle;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", departmentId=" + departmentId +
                ", jobTitle='" + jobTitle + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && gender == employee.gender && Objects.equals(departmentId, employee.departmentId) && Objects.equals(jobTitle, employee.jobTitle) && Objects.equals(dateOfBirth, employee.dateOfBirth);
    }
}
