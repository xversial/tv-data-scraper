package com.vionox.tools.tvscraper.scraper.model.plotpoint;

import lombok.Data;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@Setter
public class FrequencyPlotPoint
{
    private static final Logger LOG = LoggerFactory.getLogger(FrequencyPlotPoint.class);
    double frequencyHz;

    public void setFrequencyHz(final double frequencyHz)
    {
        this.frequencyHz = frequencyHz;
    }

    public void setFrequencyHz(final String frequencyHz)
    {
        setFrequencyHz(Double.parseDouble(frequencyHz));
    }
}
