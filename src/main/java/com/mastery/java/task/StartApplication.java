package com.mastery.java.task;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Simple web app",
                version = "0.1",
                description = "Simple web application, that realizes an employee database with \"CRUD\" operations."
        ),
        servers = @Server(
                description = "the main production server",
                url = "http://localhost:8087/simplewebapp"
        )
)
@SpringBootApplication
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}
