package com.vionox.tools.tvscraper.data.service;

import com.vionox.tools.tvscraper.data.model.devices.Soundbar;
import com.vionox.tools.tvscraper.data.model.devices.Television;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;

@Service
@EnableCaching
@CacheConfig(cacheNames = {"TelevisionDataServiceCache"})
@Transactional
public class SoundbarDataHelper
{
    private static final Logger LOG = LoggerFactory.getLogger(SoundbarDataHelper.class);
    @Autowired
    private SoundbarDataService soundbarDataService;

//    @Cacheable(unless = "#result == null")
    public ArrayList<Soundbar> getAllSoundbars()
    {
        final ArrayList<Soundbar> soundbarResult = new ArrayList<>();
        final Map<Integer, Soundbar> soundbars = soundbarDataService.getSoundbarModels();
        for (Soundbar soundbar :
                soundbars.values())
        {
            if(soundbar.getFrequencyResponse() == null){
                soundbar.setFrequencyResponse(soundbarDataService.getFrequencyResponse(soundbar));
            }
            if(soundbar.getFrequencyResponse()!=null){
                soundbarResult.add(soundbar);
            }
        }
        return soundbarResult;
    }
}
