package com.vionox.tools.tvscraper.controllers.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController
{
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
    public String index()
    {
        return "slider";
    }

    @RequestMapping(value = "/slider", method = RequestMethod.GET)
    @Secured("GUEST_ROLE")
    public String slider()
    {
        return "slider";
    }
}
