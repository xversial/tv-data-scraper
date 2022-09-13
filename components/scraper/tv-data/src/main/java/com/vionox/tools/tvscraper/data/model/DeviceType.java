package com.vionox.tools.tvscraper.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.text.WordUtils;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@SuperBuilder
@NoArgsConstructor
public class DeviceType
{
    private static final Logger LOG = LoggerFactory.getLogger(DeviceType.class);
    String title;
    String url;


    public DeviceType(Element element)
    {
        this.url = element.attr("href");
        this.title = WordUtils.capitalizeFully(element.select("span span:first-child").text());
    }
}
