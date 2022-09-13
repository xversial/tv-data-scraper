package com.vionox.tools.tvscraper.scraper.service;

import com.vionox.tools.tvscraper.scraper.contracts.ScraperService;
import com.vionox.tools.tvscraper.scraper.model.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ScraperServiceImpl implements ScraperService
{
    private static final Logger LOG = LoggerFactory.getLogger(ScraperServiceImpl.class);

    @Override
    public Set<ResponseDTO> getVehicleByModel(final String vehicleModel)
    {
        return null;
    }
}
