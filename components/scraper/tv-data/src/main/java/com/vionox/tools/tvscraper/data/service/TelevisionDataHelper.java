package com.vionox.tools.tvscraper.data.service;

import com.vionox.tools.tvscraper.data.model.devices.Television;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;

@Service
@EnableCaching
@CacheConfig(cacheNames = {"TelevisionDataServiceCache"})
@Transactional
public class TelevisionDataHelper
{
    private static final Logger LOG = LoggerFactory.getLogger(TelevisionDataHelper.class);
    @Autowired
    private TelevisionDataService televisionDataService;

//    @Cacheable(unless = "#result == null")
    public ArrayList<Television> getAllTVs()
    {
        final ArrayList<Television> televisions = new ArrayList<>();
        final Map<Integer, Television> tvs = televisionDataService.tvModelMap();
        for (Television tv :
                tvs.values())
        {
            if(tv.getFrequencyResponse() == null){
                tv.setFrequencyResponse(televisionDataService.getFrequencyResponse(tv));
            }
            if(tv.getFrequencyResponse()!=null){
                televisions.add(tv);
            }
        }
        return televisions;
    }
}
