package com.vionox.tools.tvscraper.data.model.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Data
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectronicDevice implements Serializable
{
    private static final Logger LOG = LoggerFactory.getLogger(ElectronicDevice.class);
    @JsonProperty(value = "name")
    String name;
    @JsonProperty(value = "brand")
    String brand;
    @JsonProperty(value = "model")
    String model;
    @JsonProperty(value = "id")
    int id;
}
