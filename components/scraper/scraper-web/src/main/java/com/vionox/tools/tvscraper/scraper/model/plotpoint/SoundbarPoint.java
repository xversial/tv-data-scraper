package com.vionox.tools.tvscraper.scraper.model.plotpoint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SoundbarPoint extends FrequencyPlotPoint
{
    private static final Logger LOG = LoggerFactory.getLogger(SoundbarPoint.class);
    double response1dBr;

    public void setResponse1dBr(final double response1dBr)
    {
        this.response1dBr = response1dBr;
    }

    public void setResponse1dBr(final String response1dBr)
    {
        setResponse1dBr(Double.parseDouble(response1dBr));
    }
}
