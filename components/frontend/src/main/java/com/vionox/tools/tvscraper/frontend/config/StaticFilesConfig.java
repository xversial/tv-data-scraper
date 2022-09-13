package com.vionox.tools.tvscraper.frontend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class StaticFilesConfig implements WebMvcConfigurer
{
    private static final Logger LOG = LoggerFactory.getLogger(StaticFilesConfig.class);
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/public/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/public/js/");
        registry.addResourceHandler("/vendor/**").addResourceLocations("classpath:/public/vendor/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/public/img/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/public/img/favicon.ico");
    }
}
