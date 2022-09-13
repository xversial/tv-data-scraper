package com.vionox.tools.tvscraper.data.scraping;

import com.vionox.tools.tvscraper.data.model.DeviceType;
import io.sentry.Sentry;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class RTings
{
    private static final Logger LOG = LoggerFactory.getLogger(RTings.class);

    public ArrayList<DeviceType> deviceTypes()
    {
        try {
            final ArrayList<DeviceType> deviceTypes = new ArrayList<DeviceType>();
            Document doc = Jsoup.connect("https://www.rtings.com/")
                    .userAgent("Mozilla/5.0").timeout(10 * 1000)
                    .get();
            final Elements types = doc.select(".landing_page-silo > a");

            for (Element element : types) {
                final DeviceType type = new DeviceType(element);
                deviceTypes.add(type);
            }
            return deviceTypes;

        } catch (HttpStatusException e) {
            LOG.warn(e.getMessage());
            Sentry.captureException(e);
        } catch (IOException e)
        {
            LOG.warn(e.getMessage());
            Sentry.captureException(e);
        }
        return null;
    }
}
