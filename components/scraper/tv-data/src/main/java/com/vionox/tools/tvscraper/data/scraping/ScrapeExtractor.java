package com.vionox.tools.tvscraper.data.scraping;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ScrapeExtractor
{
    private static final Logger LOG = LoggerFactory.getLogger(ScrapeExtractor.class);

    public String extractJS(Elements scriptElements, String variable)
    {
        String variableRegex = Pattern.quote(variable);
        for (Element element : scriptElements) {
            if (element.data().contains(variable)) {
                // find the line which contains 'infosite.token = <...>;'
                Pattern pattern = Pattern.compile(".*"+variableRegex+"\\s*=\\s*([^;]*);");
                Matcher matcher = pattern.matcher(element.data());
                // we only expect a single match here so there's no need to loop through the matcher's groups
                if (matcher.find()) {
                    final String match = matcher.group(1);
                    LOG.trace("Found match");
                    return match;
                } else {
                    LOG.trace("No match found!");
                }
                break;
            }
        }
        return null;
    }
}
