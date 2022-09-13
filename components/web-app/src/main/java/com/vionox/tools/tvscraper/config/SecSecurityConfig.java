package com.vionox.tools.tvscraper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;

@Configuration
@ImportResource({ "classpath:webSecurityConfig.xml" })
@Order(3)
public class SecSecurityConfig
{
    public SecSecurityConfig() {
        super();
    }
}
