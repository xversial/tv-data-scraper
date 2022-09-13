package com.vionox.tools.tvscraper.data.model.plotpoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Data
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlotPoint implements Serializable
{
    private static final Logger LOG = LoggerFactory.getLogger(PlotPoint.class);
}
