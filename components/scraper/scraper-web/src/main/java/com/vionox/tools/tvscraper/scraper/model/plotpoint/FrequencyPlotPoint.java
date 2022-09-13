package com.vionox.tools.tvscraper.scraper.model.plotpoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FrequencyPlotPoint
{
    private static final Logger LOG = LoggerFactory.getLogger(FrequencyPlotPoint.class);
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
