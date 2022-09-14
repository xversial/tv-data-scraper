package com.vionox.tools.tvscraper.data.model.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vionox.tools.tvscraper.data.model.graph.TVGraphData;
import io.sentry.Sentry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

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

    TVGraphData frequencyResponse;

    /*@Cacheable(value = "tvFrequencyResponse", key = "#root.target.id", unless = "#result == null", cacheManager =
            "cacheManager")
    public TVGraphData getFrequencyResponse()
    {
        try
        {
            String url = "https://www.rtings.com/graph/data/" + this.getId() + "/13812";
            LOG.trace("Fetching graph for TV {} with id {}", this.getName(), this.getId());
            RestTemplate restTemplate = new RestTemplate();
            final TVGraphData graphData = restTemplate.getForObject(url, TVGraphData.class);
            if (graphData == null) return null;
//            graphData.setTelevision(this);
            this.frequencyResponse = graphData;
            return graphData;
        } catch (RuntimeException e)
        {
            LOG.warn(e.getMessage());
            Sentry.captureException(e);
        }

        return null;
    }*/
}
