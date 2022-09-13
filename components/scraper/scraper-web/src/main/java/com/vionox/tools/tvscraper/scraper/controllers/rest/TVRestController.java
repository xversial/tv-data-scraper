package com.vionox.tools.tvscraper.scraper.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Object fetchAll(HttpServletRequest request) {
        return "Not implemented yet";
    }
}
