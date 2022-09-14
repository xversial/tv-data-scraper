package com.vionox.tools.tvscraper.scraper.controllers.rest;

import com.vionox.tools.tvscraper.data.model.GraphData;
import com.vionox.tools.tvscraper.data.model.devices.Television;
import com.vionox.tools.tvscraper.data.model.plotpoint.TVPoint;
import com.vionox.tools.tvscraper.data.scraping.RTings;
import com.vionox.tools.tvscraper.data.service.TelevisionDataHelper;
import com.vionox.tools.tvscraper.data.service.TelevisionDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("rest/data/tv")
public class TVRestController
{
    private static final Logger LOG = LoggerFactory.getLogger(TVRestController.class);
    @Autowired
    private TelevisionDataService televisionDataService;
    @Autowired
    private TelevisionDataHelper televisionDataHelper;


    private ArrayList<Television> getAllTVs()
    {
        final ArrayList<Television> televisions = new ArrayList<>();
        final Map<Integer, Television> tvs = televisionDataService.tvModelMap();
        for (Television tv :
                tvs.values())
        {
            if(tv.getFrequencyResponse() == null){
                tv.setFrequencyResponse(televisionDataService.getFrequencyResponse(tv));
            }
            if(tv.getFrequencyResponse()!=null){
                televisions.add(tv);
            }
        }
        return televisions;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Object fetchAll(HttpServletRequest request) {

//        final ArrayList<Television> tvs = televisionDataHelper.getAllTVs();
        final ArrayList<Television> tvs = getAllTVs();
        return tvs;
    }

    @RequestMapping(value = "/csv", method = RequestMethod.GET)
    @ResponseBody
    public Object fetchAllCSV(HttpServletRequest request) {
        final Map<Integer, Television> tvs = televisionDataService.getTVModels();


        return tvs;
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

        return televisionDataService.getTVModels();
    }

    @RequestMapping(value = "/graph/{tvID}", method = RequestMethod.GET)
    @ResponseBody
    public GraphData tv(HttpServletRequest request, @PathVariable(value = "tvID") String tvID) {
        LOG.trace("Getting TV Graph from controller");
        final Television tv = televisionDataService.getTV(tvID);
        final GraphData tvGraph = televisionDataService.getFrequencyResponse(tv);
        return tvGraph;
    }

    @RequestMapping(value = "/tv/{tvID}", method = RequestMethod.GET)
    @ResponseBody
    public Television getTv(HttpServletRequest request, @PathVariable(value = "tvID") String tvID) {
        return televisionDataService.getTV(tvID, true);
    }
}
