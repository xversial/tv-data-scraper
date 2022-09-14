package com.vionox.tools.tvscraper.data.scraping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.vionox.tools.tvscraper.data.model.GraphData;
import com.vionox.tools.tvscraper.data.model.devices.Soundbar;
import com.vionox.tools.tvscraper.data.model.devices.Television;
import com.vionox.tools.tvscraper.data.model.graph.TVGraphData;
import io.sentry.Sentry;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class RTingsTV
{
    private static final Logger LOG = LoggerFactory.getLogger(RTingsTV.class);

    @Autowired
    private ScraperWebFetch scraperWebFetch;
    @Autowired
    private ScrapeExtractor scrapeExtractor;

    private Map<Integer, Television> objectMapper(String jsonString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            Map map3 =
                    mapper.readValue(jsonString, new TypeReference<Map<Integer, Television>>(){});
            final Collection valueSet = map3.values();

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
    private Map<Integer, Soundbar> objectMapperSoundbar(String jsonString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            Map map3 =
                    mapper.readValue(jsonString, new TypeReference<Map<Integer, Soundbar>>(){});

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

    protected static <T> List<T> mapJsonToObjectList(String json, Class<T> clazz) throws Exception
    {
        List<T> list;
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(json);
        TypeFactory t = TypeFactory.defaultInstance();
        list = mapper.readValue(json, t.constructCollectionType(ArrayList.class,clazz));

        System.out.println(list);
        System.out.println(list.get(0).getClass());
        return list;
    }

    private Object getDeviceModelJson(Document doc)
    {
        LOG.trace("Fetching TV Models");
        Elements scriptElements = doc.getElementsByTag("script");
        return scrapeExtractor.extractJS(scriptElements, "products_info");
    }

    public Collection<Television> tvModelList()
    {
        final Object products_info = deviceModelJson("tv");
        final Map<Integer, Television> tvList = objectMapper(String.valueOf(products_info));
        return tvList.values();
    }

    public Object deviceModelJson(String deviceType)
    {
        final Document doc = scraperWebFetch.fetch("https://www.rtings.com/"+deviceType+"/graph");
        return getDeviceModelJson(doc);
    }
    public Map<Integer, Soundbar> soundbarModelMap()
    {
        final Object products_info = deviceModelJson("soundbar");
        final Map<Integer, Soundbar> soundbarList = objectMapperSoundbar(String.valueOf(products_info));
        return soundbarList;
    }

    public Map<Integer, Television> tvModelMap()
    {
        final Object products_info = deviceModelJson("tv");
        final Map<Integer, Television> tvList = objectMapper(String.valueOf(products_info));
        return tvList;
    }

    public Television findTV(int id)
    {
        final Map<Integer, Television> tvList = tvModelMap();
        tvList.values().toArray();
        for (Television tv :
                tvList.values())
        {
            if(tv.getId()==id){
                return tv;
            }
        }
        return null;
    }

    public GraphData getTVGraph(int id)
    {
        final Television tv = findTV(id);
        final GraphData frequencyResponse = getFrequencyResponse(tv);
        return frequencyResponse;
    }

    public GraphData getFrequencyResponse(Television tv)
    {
//        final ArrayList<TVPoint> tvPoints = new ArrayList<>();

        try
        {
            String url = "https://www.rtings.com/graph/data/"+tv.getId()+"/13812";
            RestTemplate restTemplate = new RestTemplate();
//            final String response = restTemplate.getForObject(url, String.class);

            /*ObjectMapper mapper = new ObjectMapper();
            GraphData map3 =
                    mapper.readValue(response, TVGraphData.class);*/

//            final Document fetch = scraperWebFetch.fetch(url);

//                    mapper.readValue(response, new TypeReference<Map<Integer, Television>>(){});


            final TVGraphData forObject = restTemplate.getForObject(url, TVGraphData.class);

            LOG.trace("Done");
            return forObject;

        } catch (RuntimeException e)
        {
            LOG.warn(e.getMessage());
            Sentry.captureException(e);
        }

        return null;
    }

    public Map<Integer, Television> getTVModels()
    {
        final ArrayList<Television> televisionModels = new ArrayList<>();
        final Map<Integer, Television> tvList = tvModelMap();

        LOG.trace("Test");

        if(true){
            return tvList;
        }
/*
        final Elements tvOptionSet = doc.select("body select#product_select option");

        for (Element tvOption : tvOptionSet) {
            final String id = tvOption.attr("value");
            final String productName = tvOption.text();
            if(StringUtils.isEmpty(id) || StringUtils.isBlank(productName)){
                continue;
            }
            final Television tv = Television.builder()
                    .id(Integer.parseInt(id))
                    .name(productName)
                    .build();
            televisionModels.add(tv);
        }
        return televisionModels;*/

        return null;
    }

    public Object getTVs()
    {
        return null;
    }
}
