package com.mucis.analysis;

import com.mucis.model.Report;
import com.mucis.repository.ReportRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/report", produces = {"application/json"})
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Report> handleFileUpload(Pageable pageable) {
        return reportRepository.find(pageable);
    }

    @RequestMapping(method = RequestMethod.GET, value = "day/{date}")
    public List<Report> handleFileUpload(
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
        Pageable pageable) {
        return reportRepository.find(date, pageable);
    }

    @RequestMapping(method = RequestMethod.GET, value = "product/{upc}")
    public List<Report> findByUPC(@PathVariable String upc, Pageable pageable) {
        return reportRepository.findByUPC(upc, pageable);
    }

    @RequestMapping(method = RequestMethod.GET, value = "asset/{isrc}")
    public List<Report> findByISRC(@PathVariable String isrc, Pageable pageable) {
        return reportRepository.findByISRC(isrc, pageable);
    }

}
