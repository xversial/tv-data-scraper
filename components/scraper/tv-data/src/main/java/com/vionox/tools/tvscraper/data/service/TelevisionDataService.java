package com.vionox.tools.tvscraper.data.service;

import com.vionox.tools.tvscraper.data.model.devices.Television;
import com.vionox.tools.tvscraper.data.model.graph.TVGraphData;
import com.vionox.tools.tvscraper.data.scraping.RTingsTV;
import com.vionox.tools.tvscraper.data.scraping.ScrapeExtractor;
import com.vionox.tools.tvscraper.data.scraping.ScraperWebFetch;
import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class TelevisionDataService
{
    private static final Logger LOG = LoggerFactory.getLogger(TelevisionDataService.class);
    @Autowired
    private ScraperWebFetch scraperWebFetch;
    @Autowired
    private ScrapeExtractor scrapeExtractor;
    @Autowired
    private RTingsTV rtingsTV;

    public TVGraphData getFrequencyResponse(String tvID)
    {
        return this.getFrequencyResponse(Integer.parseInt(tvID));
    }

    public TVGraphData getFrequencyResponse(int tvID)
    {
        Television tv = this.getTV(tvID);
        return this.getFrequencyResponse(tv);
    }

    @Cacheable(value = "tvFrequencyResponse", key = "#tv.id")
    public TVGraphData getFrequencyResponse(Television tv)
    {
        try
        {
            String url = "https://www.rtings.com/graph/data/" + tv.getId() + "/13812";
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(url, TVGraphData.class);
        } catch (RuntimeException e)
        {
            LOG.warn(e.getMessage());
            Sentry.captureException(e);
        }

        return null;
    }

    public Map<Integer, Television> getTVModels()
    {
        return rtingsTV.getTVModels();
    }

    public Television getTV(String id)
    {
        return getTV(Integer.parseInt(id));
    }

    public Television getTV(int id)
    {
        final Map<Integer, Television> tvList = rtingsTV.tvModelList();
        tvList.values().toArray();
        for (Television tv : tvList.values())
        {
            if (tv.getId() == id)
            {
                return tv;
            }
        }
        return null;
    }
}
