package com.vionox.tools.tvscraper.scraper.controllers.rest;

import com.vionox.tools.tvscraper.data.scraping.RTings;
import com.vionox.tools.tvscraper.data.model.plotpoint.TVPoint;
import com.vionox.tools.tvscraper.data.scraping.RTingsTV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("rest/data/tv")
public class TVRestController
{
    private static final Logger LOG = LoggerFactory.getLogger(TVRestController.class);

    @Autowired
    private RTingsTV rtingsTV;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Object fetchAll(HttpServletRequest request) {

        final TVPoint tvPoint = TVPoint.builder()
                .frequencyHz(1)
                .build();
        tvPoint.setFrequencyHz(2.0000001112);
        return tvPoint;
    }
    @RequestMapping(value = "/rtings", method = RequestMethod.GET)
    @ResponseBody
    public Object rtings(HttpServletRequest request) {

        final RTings rTings = new RTings();
        return rTings.deviceTypes();
    }

    @RequestMapping(value = "/tv", method = RequestMethod.GET)
    @ResponseBody
    public Object tv(HttpServletRequest request) {

        return rtingsTV.getTVModels();
    }
}
