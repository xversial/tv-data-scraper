package com.vionox.tools.tvscraper.scraper.service;

import com.vionox.tools.tvscraper.data.model.devices.Television;
import com.vionox.tools.tvscraper.data.model.plotpoint.TVPoint;
import com.vionox.tools.tvscraper.data.service.TelevisionDataHelper;
import com.vionox.tools.tvscraper.data.service.TelevisionDataService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CsvExportService
{
    private static final Logger log = LoggerFactory.getLogger(CsvExportService.class);
    @Autowired
    private TelevisionDataService televisionDataService;
    @Autowired
    private TelevisionDataHelper televisionDataHelper;

    public void writeTelevisionsToCSV(Writer writer)
    {
        final ArrayList<Television> tvs = televisionDataHelper.getAllTVs();

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            for (Television tv : tvs) {

                final List<TVPoint> frd = tv.getFrequencyResponse().getData();
                List<TVPoint> fr = frd.subList(frd.size()-6, frd.size());

                csvPrinter.printRecord(
                        tv.getId(), tv.getName(), tv.getBrand(),
                        fr.get(0).getFrequencyHz(),
                        fr.get(0).getResponse1dBSPL(),
                        fr.get(1).getFrequencyHz(),
                        fr.get(1).getResponse1dBSPL(),
                        fr.get(2).getFrequencyHz(),
                        fr.get(2).getResponse1dBSPL(),
                        fr.get(3).getFrequencyHz(),
                        fr.get(3).getResponse1dBSPL(),
                        fr.get(4).getFrequencyHz(),
                        fr.get(4).getResponse1dBSPL(),
                        fr.get(5).getFrequencyHz(),
                        fr.get(5).getResponse1dBSPL()

                );
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }
}
