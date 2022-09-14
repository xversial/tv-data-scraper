package com.vionox.tools.tvscraper.data.model.graph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vionox.tools.tvscraper.data.model.GraphData;
import com.vionox.tools.tvscraper.data.model.plotpoint.SoundbarPoint;
import com.vionox.tools.tvscraper.data.model.plotpoint.TVPoint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@ToString
public class SoundbarGraphData extends GraphData implements Serializable
{
    private static final Logger LOG = LoggerFactory.getLogger(SoundbarGraphData.class);

    @JsonProperty(value = "data")
    List<SoundbarPoint> data;
}
