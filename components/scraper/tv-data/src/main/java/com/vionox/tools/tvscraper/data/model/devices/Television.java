package com.vionox.tools.tvscraper.data.model.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vionox.tools.tvscraper.data.model.GraphData;
import com.vionox.tools.tvscraper.data.model.graph.TVGraphData;
import com.vionox.tools.tvscraper.data.model.plotpoint.TVPoint;
import io.sentry.Sentry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@ToString
public class Television extends ElectronicDevice implements Serializable
{
    private static final Logger LOG = LoggerFactory.getLogger(Television.class);

    public TVGraphData getFrequencyResponse()
    {
        try
        {String url = "https://www.rtings.com/graph/data/"+this.getId()+"/13812";
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(url, TVGraphData.class);
        } catch (RuntimeException e)
        {
            LOG.warn(e.getMessage());
            Sentry.captureException(e);
        }

        return null;
    }
}
