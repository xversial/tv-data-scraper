package com.vionox.tools.tvscraper.scraper.controllers.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("data/tv")
public class TVDataController
{
    private static final Logger LOG = LoggerFactory.getLogger(TVDataController.class);

    @RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
    public String index()
    {
        return "slider";
    }
}
