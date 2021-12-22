package com.mastery.java.task.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for serializing/deserializing operations.
 */
@Configuration
public class JsonConfiguration {

    /**
     * Configures JSON mapper for serializing/deserializing.
     * @return configured {@link ObjectMapper} instance, which includes module for mapping of {@link java.time.LocalDate} java objects.
     */
    @Bean
    public ObjectMapper jacksonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
