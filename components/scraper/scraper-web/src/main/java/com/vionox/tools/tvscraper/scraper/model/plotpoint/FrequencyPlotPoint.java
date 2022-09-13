package com.vionox.tools.tvscraper.scraper.model.plotpoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
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
