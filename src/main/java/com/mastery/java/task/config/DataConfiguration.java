package com.mastery.java.task.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource(value = "classpath:db.properties")
@ComponentScan({"com.mastery.java.task.dao"})
public class DataConfiguration {

    @Value("${dbname}")
    private String databaseName;
    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(databaseName);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
