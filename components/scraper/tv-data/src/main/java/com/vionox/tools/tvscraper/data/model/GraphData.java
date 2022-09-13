package com.vionox.tools.tvscraper.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vionox.tools.tvscraper.data.model.plotpoint.PlotPoint;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphData implements Serializable
{
    private static final Logger LOG = LoggerFactory.getLogger(GraphData.class);

    @JsonProperty(value = "header")
    ArrayList<String> header;
    @JsonProperty(value = "data")
    ArrayList<PlotPoint> data;
    @JsonProperty(value = "options")
    GraphOptions options;
}
