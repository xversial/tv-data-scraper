package com.vionox.tools.tvscraper.data.model.plotpoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Data
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class FrequencyPlotPoint extends PlotPoint implements Serializable
{
    private static final Logger LOG = LoggerFactory.getLogger(FrequencyPlotPoint.class);
    @JsonProperty(value = "frequencyHz")
    double frequencyHz;

    // <editor-fold desc="Setter methods">
    public void setFrequencyHz(final double frequencyHz)
    {
        this.frequencyHz = frequencyHz;
    }
    // </editor-fold>

    // <editor-fold desc="String setter methods">
    public void setFrequencyHz(final String frequencyHz)
    {
        setFrequencyHz(Double.parseDouble(frequencyHz));
    }
    // </editor-fold>
}
