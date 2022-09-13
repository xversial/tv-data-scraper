package com.vionox.tools.tvscraper.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphOptions implements Serializable
{
    private static final Logger LOG = LoggerFactory.getLogger(GraphOptions.class);
    String title;
}
