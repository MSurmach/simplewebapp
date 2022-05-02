package com.mastery.java.task.dto;

import com.mastery.java.task.annotation.IsAdult;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Simple blueprint for creating Employee instances, which later on will be added into a database.
 */
@Entity
@Table(name = "employee", schema = "public")
public class Employee {
    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "first_name")
    private String firstname;

    @NotEmpty
    @Column(name = "last_name")
    private String lastname;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "job_title")
    private String jobTitle;

    @IsAdult
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    protected Employee() {
    }

    public Employee(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Employee(String firstname, String lastname, Gender gender, Long departmentId, String jobTitle, LocalDate dateOfBirth) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.departmentId = departmentId;
        this.jobTitle = jobTitle;
        this.dateOfBirth = dateOfBirth;
    }

    public Employee(Employee that) {
        this.id = that.id;
        this.firstname = that.firstname;
        this.lastname = that.lastname;
        this.gender = that.gender;
        this.departmentId = that.departmentId;
        this.jobTitle = that.jobTitle;
        this.dateOfBirth = that.dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
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
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
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
        return Objects.equals(id, employee.id) && Objects.equals(firstname, employee.firstname) && Objects.equals(lastname, employee.lastname) && gender == employee.gender && Objects.equals(departmentId, employee.departmentId) && Objects.equals(jobTitle, employee.jobTitle) && Objects.equals(dateOfBirth, employee.dateOfBirth);
    }
}
