package com.mastery.java.task.jms;

import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeJmsProducer {

    private JmsTemplate jmsTemplate;

    @Value("${app.activemq.queue}")
    private String destinationQueue;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void produce(Employee employee) {
        jmsTemplate.convertAndSend(destinationQueue, employee);
    }
}
