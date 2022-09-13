package com.vionox.tools.tvscraper.data.scraping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.vionox.tools.tvscraper.data.model.DeviceType;
import com.vionox.tools.tvscraper.data.model.GraphData;
import com.vionox.tools.tvscraper.data.model.devices.Television;
import io.sentry.Sentry;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class RTingsTV
{
    private static final Logger LOG = LoggerFactory.getLogger(RTingsTV.class);

    @Autowired
    private ScraperWebFetch scraperWebFetch;
    @Autowired
    private ScrapeExtractor scrapeExtractor;

    private Object objectMapper(String jsonString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            JsonNode jsonNode = mapper.readTree(jsonString);
            String sourceString = jsonString;
//            String sourceString = jsonNode.at("/results").toString();
            LinkedHashMap<String, Television> map = mapper.readValue(jsonString, LinkedHashMap.class);
            mapJsonToObjectList(jsonString, Television.class);

            Television[] tv = mapper.readValue(sourceString, Television[].class);
            return tv;
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

    private Object getTVModelJson(Document doc)
    {
        LOG.trace("Fetching TV Models");
        Elements scriptElements = doc.getElementsByTag("script");
        return scrapeExtractor.extractJS(scriptElements, "products_info");
    }

    public Object getTVModels()
    {
        final ArrayList<Television> televisionModels = new ArrayList<>();

        final Document doc = scraperWebFetch.fetch("https://www.rtings.com/tv/graph");
        final Object products_info = getTVModelJson(doc);
        final Object tvList = objectMapper(String.valueOf(products_info));

        LOG.trace("Test");

        if(true){
            return products_info;
        }

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
        return televisionModels;

    }

    public Object getTVs()
    {
        return null;
    }
}
