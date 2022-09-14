package com.vionox.tools.tvscraper.scraper.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching
public class CachingConfig
{
    private static final Logger LOG = LoggerFactory.getLogger(CachingConfig.class);
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("directory"),
                new ConcurrentMapCache("televisionModels"),
                new ConcurrentMapCache("tvFrequencyResponse"),
                new ConcurrentMapCache("addresses")));
        return cacheManager;
    }
}
