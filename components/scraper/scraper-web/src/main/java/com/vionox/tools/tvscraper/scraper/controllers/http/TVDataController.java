package com.vionox.tools.tvscraper.scraper.controllers.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("data/tv")
public class TVDataController
{
    private static final Logger LOG = LoggerFactory.getLogger(TVDataController.class);

    @RequestMapping(value = {"/fetch-all"}, method = RequestMethod.GET)
    public ModelAndView fetchAll()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/error-500");
        modelAndView.addObject("error", 404);
        return modelAndView;
    }

    @RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
    public String index()
    {
        return "slider";
    }
}
