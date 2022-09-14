package com.vionox.tools.tvscraper.data.model.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@ToString
public class ElectronicDevice implements Serializable
{
    private static final Logger LOG = LoggerFactory.getLogger(ElectronicDevice.class);
    @JsonProperty(value = "name")
    String name;
    @JsonProperty(value = "brand")
    String brand;
    @JsonProperty(value = "model")
    String model;
    @JsonProperty(value = "comp_ids")
    List<Integer> comparisonID;
    @JsonProperty(value = "id")
    int id;
}
