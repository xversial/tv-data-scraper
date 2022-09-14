package com.vionox.tools.tvscraper.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vionox.tools.tvscraper.data.model.devices.ElectronicDevice;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelObject
{
    private static final Logger LOG = LoggerFactory.getLogger(ModelObject.class);
    private int id;
//    @JsonProperty("data")
    private ElectronicDevice value;
}
