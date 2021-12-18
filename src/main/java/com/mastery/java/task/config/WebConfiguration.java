package com.mastery.java.task.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan({"com.mastery.java.task.rest", "com.mastery.java.task.service"})
public class WebConfiguration {
}
