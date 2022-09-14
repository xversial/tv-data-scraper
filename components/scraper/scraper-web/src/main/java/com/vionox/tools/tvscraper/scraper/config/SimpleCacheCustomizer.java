package com.vionox.tools.tvscraper.scraper.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

@Component
@EnableCaching
public class SimpleCacheCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager>
{
    private static final Logger LOG = LoggerFactory.getLogger(SimpleCacheCustomizer.class);
    @Override
    public void customize(ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(asList("users",
                "transactions",
                "TelevisionDataServiceCache",
                "televisionModels",
                "soundbarFrequencyResponse",
                "tvFrequencyResponse"));
    }
}
