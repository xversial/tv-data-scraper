package com.vionox.tools.tvscraper.scraper.contracts;

import com.vionox.tools.tvscraper.scraper.model.ResponseDTO;

import java.util.Set;

public interface ScraperService
{
    Set<ResponseDTO> getVehicleByModel(String vehicleModel);
}
