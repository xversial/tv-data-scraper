package com.vionox.tools.tvscraper.scraper.controllers.rest;

import com.vionox.tools.tvscraper.data.model.devices.Soundbar;
import com.vionox.tools.tvscraper.data.service.SoundbarDataHelper;
import com.vionox.tools.tvscraper.data.service.SoundbarDataService;
import com.vionox.tools.tvscraper.scraper.service.CsvExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("rest/data/soundbar")
public class SoundbarRestController
{
    private static final Logger log = LoggerFactory.getLogger(SoundbarRestController.class);
    @Autowired
    private SoundbarDataService soundbarDataService;
    @Autowired
    private SoundbarDataHelper soundbarDataHelper;
    @Autowired
    private CsvExportService csvExportService;

    @RequestMapping(value = "/csv", method = RequestMethod.GET)
    @ResponseBody
    public Object fetchAllCSV(HttpServletRequest request, HttpServletResponse response) {
        final Map<Integer, Soundbar> tvs = soundbarDataService.getSoundbarModels();
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition","attachment; filename=\"soundbars.csv\"");
        try
        {
            csvExportService.writeSoundbarsToCSV(response.getWriter());
        } catch (IOException e)
        {
            log.warn(e.getMessage());
        }

        return tvs;
    }
}
