package com.vionox.tools.tvscraper.data.model.plotpoint;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Data
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(shape= JsonFormat.Shape.ARRAY)
@NoArgsConstructor
public class PlotPoint implements Serializable
{
    private static final Logger LOG = LoggerFactory.getLogger(PlotPoint.class);
}
