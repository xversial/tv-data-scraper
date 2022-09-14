package com.vionox.tools.tvscraper.data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vionox.tools.tvscraper.data.model.devices.Soundbar;
import com.vionox.tools.tvscraper.data.model.devices.Television;
import com.vionox.tools.tvscraper.data.model.graph.SoundbarGraphData;
import com.vionox.tools.tvscraper.data.model.graph.TVGraphData;
import com.vionox.tools.tvscraper.data.scraping.RTingsTV;
import com.vionox.tools.tvscraper.data.scraping.ScrapeExtractor;
import com.vionox.tools.tvscraper.data.scraping.ScraperWebFetch;
import io.sentry.Sentry;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@EnableCaching
@CacheConfig(cacheNames = {"TelevisionDataServiceCache"})
@Transactional
public class SoundbarDataService implements Runnable, ApplicationListener<ContextRefreshedEvent>
{
    private static final Logger LOG = LoggerFactory.getLogger(SoundbarDataService.class);
    @Autowired
    private ScraperWebFetch scraperWebFetch;
    @Autowired
    private ScrapeExtractor scrapeExtractor;
    @Autowired
    private RTingsTV rtingsTV;

    @Autowired
    private CacheManager cacheManager;

    @Cacheable(value = "tvFrequencyResponse", key = "#tvID", unless = "#result == null", cacheManager = "cacheManager")
    public TVGraphData getFrequencyResponse(String tvID)
    {
        return this.getFrequencyResponse(Integer.parseInt(tvID));
    }

    @Cacheable(value = "tvFrequencyResponse", key = "#tvID", unless = "#result == null", cacheManager = "cacheManager")
    public TVGraphData getFrequencyResponse(int tvID)
    {
        Television tv = this.getTV(tvID);
        return this.getFrequencyResponse(tv);
    }

    //    @Cacheable(value = "tvFrequencyResponse", key = "#tv.id", unless="#result == null")
//    @CachePut(value = "tvFrequencyResponse", key = "#tv.id", unless="#result == null")
//    @CachePut(value = "tvFrequencyResponse", cacheManager = "cacheManager")
    @Cacheable(value = "tvFrequencyResponse", key = "#tv.id", unless = "#result == null", cacheManager = "cacheManager")
    public TVGraphData getFrequencyResponse(Television tv)
    {
        try
        {
            LOG.trace("Requesting graph data on frequency response for the {} (TV {})", tv.getName(), tv.getId());
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(scraperWebFetch.getGraphURL(tv.getId(), 13812), TVGraphData.class);
        } catch (RuntimeException e)
        {
            LOG.warn(e.getMessage());
            Sentry.captureException(e);
        }
        return null;
    }

    @Cacheable(value = "soundbarFrequencyResponse", key = "#soundbar.id", unless = "#result == null", cacheManager = "cacheManager")
    public SoundbarGraphData getFrequencyResponse(Soundbar soundbar)
    {
        try
        {
            LOG.trace("Requesting graph data on frequency response for the {} (Soundbar {})", soundbar.getName(), soundbar.getId());
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(scraperWebFetch.getGraphURL(soundbar.getId(), 7114), SoundbarGraphData.class);
        } catch (RuntimeException e)
        {
            LOG.warn(e.getMessage());
            Sentry.captureException(e);
        }
        return null;
    }

    private Object getTVModelJson(Document doc)
    {
        LOG.trace("Fetching TV Models");
        Elements scriptElements = doc.getElementsByTag("script");
        return scrapeExtractor.extractJS(scriptElements, "products_info");
    }

    private Map<Integer, Television> objectMapper(String jsonString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            Map map3 = mapper.readValue(jsonString, new TypeReference<Map<Integer, Television>>()
            {
            });

            return map3;
        } catch (JsonProcessingException e)
        {
            LOG.warn(e.getMessage());
            Sentry.captureException(e);
        } catch (Exception e)
        {
            LOG.warn(e.getMessage());
            Sentry.captureException(e);
        }
        return null;
    }

    @Cacheable(unless = "#result == null")
    public Map<Integer, Television> tvModelMap()
    {
        final Document doc = scraperWebFetch.fetch("https://www.rtings.com/tv/graph");
        final Object products_info = getTVModelJson(doc);
        final Map<Integer, Television> tvList = objectMapper(String.valueOf(products_info));
        return tvList;
    }

//    @Cacheable(unless = "#result == null", cacheManager = "cacheManager")
    public Map<Integer, Soundbar> getSoundbarModels()
    {
        return rtingsTV.soundbarModelMap();
    }

    @Cacheable(key = "#id", unless = "#result == null || result.frequencyResponse == null", cacheManager = "cacheManager")
    public Television getTV(String id)
    {
        return getTV(Integer.parseInt(id));
    }

    @Cacheable(key = "#id", unless = "#result == null || result.frequencyResponse == null", cacheManager = "cacheManager")
    public Television getTV(int id)
    {
        return getTV(id, true);
    }

    @Cacheable(key = "#id", unless = "#result == null || result.frequencyResponse == null || #full == false", cacheManager = "cacheManager")
    public Television getTV(String id, boolean full)
    {
        return getTV(Integer.parseInt(id), full);
    }

    @Cacheable(key = "#id", unless = "#result == null || #full==false", cacheManager = "cacheManager")
    public Television getTV(int id, boolean full)
    {
        LOG.trace("Finding{} TV with id {}", (full ? " full model" : ""), id);

        final Map<Integer, Television> tvList = rtingsTV.tvModelMap();
        tvList.values().toArray();
        for (Television tv : tvList.values())
        {
            if (tv.getId() == id)
            {
                LOG.trace("Found TV {} with id {}", tv.getName(), tv.getId());
                if (full)
                {
                    LOG.trace("Getting frequency response to add to model");
                    tv.setFrequencyResponse(getFrequencyResponse(tv));
                }
                return tv;
            }
        }
        return null;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run()
    {
        LOG.trace("Run method called");
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        String name = event.getApplicationContext().getDisplayName();

        if (name.contains("Root"))
        {
            // ANY CODE That requires @Cacheable is called HERE!..
            // Will not work if ran before this, such as inside @PostConstruct
            LOG.trace("Ready to cache!");
        }
    }
}
