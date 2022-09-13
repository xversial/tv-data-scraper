package com.vionox.tools.tvscraper.data.model.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Soundbar extends ElectronicDevice implements Serializable
{
    private static final Logger LOG = LoggerFactory.getLogger(Soundbar.class);
}
