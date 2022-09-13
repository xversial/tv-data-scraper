package com.vionox.tools.tvscraper.data.scraping;

import io.sentry.Sentry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScraperWebFetch
{
    private static final Logger LOG = LoggerFactory.getLogger(ScraperWebFetch.class);

    public Document fetch(String url)
    {
        try
        {
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0").timeout(10 * 1000)
                    .get();
        } catch (IOException e)
        {
            LOG.warn(e.getMessage());
            Sentry.captureException(e);
        }
        return null;
    }
}
