package com.mastery.java.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Spring configuration for the web context of the application.
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.mastery.java.task.rest", "com.mastery.java.task.service"})
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * @return
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * Adds a controller, which returns a root view of the application.
     *
     * @param registry assists with the registration of simple automated controllers pre-configured with status code and/or a view.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }

    /**
     * Provides the URL path patterns for which the handler should be invoked to serve static resources.
     *
     * @param registry stores registrations of resource handlers for serving static resources such as CSS styles and JavaScript scripts.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/styles/**").addResourceLocations("/WEB-INF/styles/");
        registry.addResourceHandler("/scripts/**").addResourceLocations("/WEB-INF/scripts/");
    }
}

