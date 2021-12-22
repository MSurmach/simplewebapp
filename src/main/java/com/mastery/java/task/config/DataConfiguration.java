package com.mastery.java.task.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Spring configuration for the database connections.
 */
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

    /**
     * Configures JDBC link to a database. For database connection {@link javax.sql.DataSource} instance is used, which does not perform connection pooling.
     * @return configured {@link JdbcTemplate} object, which is used for getting connection to a database.
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(databaseName);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return new JdbcTemplate(dataSource);
    }
}
