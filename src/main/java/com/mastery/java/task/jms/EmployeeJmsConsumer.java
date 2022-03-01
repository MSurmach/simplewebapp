package com.mastery.java.task.jms;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeJmsConsumer {

    private EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @JmsListener(destination = "EmployeeQueue")
    public void consume(Employee employee) {
        employeeRepository.save(employee);
    }
}
