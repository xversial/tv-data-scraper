package com.vionox.tools.tvscraper.data.model.plotpoint;

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
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "frequencyHz","response1dBr"})
public class SoundbarPoint extends FrequencyPlotPoint implements Serializable
{
    private static final Logger LOG = LoggerFactory.getLogger(SoundbarPoint.class);
    double response1dBr;

    // <editor-fold desc="Setter methods">
    public void setResponse1dBr(final double response1dBr)
    {
        this.response1dBr = response1dBr;
    }
    // </editor-fold>

    // <editor-fold desc="String setter methods">
    public void setResponse1dBr(final String response1dBr)
    {
        setResponse1dBr(Double.parseDouble(response1dBr));
    }
    // </editor-fold>
}
