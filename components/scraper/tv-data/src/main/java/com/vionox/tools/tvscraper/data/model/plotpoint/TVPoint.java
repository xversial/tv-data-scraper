package com.vionox.tools.tvscraper.data.model.plotpoint;

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
public class TVPoint extends FrequencyPlotPoint implements Serializable
{
    private static final Logger LOG = LoggerFactory.getLogger(TVPoint.class);
    double response1dBSPL;
    double volume1dBSPL;
    double response2dBSPL;
    double volume2dBSPL;
    double responseMAXdBSPL;
    double volumeMAXdBSPL;

    // <editor-fold desc="Setter methods">
    public void setResponse1dBSPL(final double response1dBSPL)
    {
        this.response1dBSPL = response1dBSPL;
    }

    public void setVolume1dBSPL(final double volume1dBSPL)
    {
        this.volume1dBSPL = volume1dBSPL;
    }

    public void setResponse2dBSPL(final double response2dBSPL)
    {
        this.response2dBSPL = response2dBSPL;
    }

    public void setVolume2dBSPL(final double volume2dBSPL)
    {
        this.volume2dBSPL = volume2dBSPL;
    }

    public void setResponseMAXdBSPL(final double responseMAXdBSPL)
    {
        this.responseMAXdBSPL = responseMAXdBSPL;
    }

    public void setVolumeMAXdBSPL(final double volumeMAXdBSPL)
    {
        this.volumeMAXdBSPL = volumeMAXdBSPL;
    }
    // </editor-fold>

    // <editor-fold desc="String setter methods">
    public void setResponse1dBSPL(final String response1dBSPL)
    {
        setResponse1dBSPL(Double.parseDouble(response1dBSPL));
    }

    public void setVolume1dBSPL(final String volume1dBSPL)
    {
        setVolume1dBSPL(Double.parseDouble(volume1dBSPL));
    }

    public void setResponse2dBSPL(final String response2dBSPL)
    {
        setResponse2dBSPL(Double.parseDouble(response2dBSPL));
    }

    public void setVolume2dBSPL(final String volume2dBSPL)
    {
        setVolume2dBSPL(Double.parseDouble(volume2dBSPL));
    }

    public void setResponseMAXdBSPL(final String responseMAXdBSPL)
    {
        setResponseMAXdBSPL(Double.parseDouble(responseMAXdBSPL));
    }

    public void setVolumeMAXdBSPL(final String volumeMAXdBSPL)
    {
        setVolumeMAXdBSPL(Double.parseDouble(volumeMAXdBSPL));
    }
    // </editor-fold>
}
