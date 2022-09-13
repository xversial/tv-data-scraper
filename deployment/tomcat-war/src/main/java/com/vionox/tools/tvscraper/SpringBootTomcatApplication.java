package com.vionox.tools.tvscraper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.vionox.tools.tvscraper"})  // force scan JPA entities
@EnableConfigurationProperties
@ComponentScan(basePackages = {"com.vionox.tools.tvscraper"})
@SpringBootConfiguration
@EnableJpaRepositories(basePackages = {"com.vionox.tools.tvscraper"})
public class SpringBootTomcatApplication extends SpringBootServletInitializer
{
    private static final Logger LOG = LoggerFactory.getLogger(SpringBootTomcatApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTomcatApplication.class, args);
    }
}
