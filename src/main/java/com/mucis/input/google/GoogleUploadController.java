package com.mucis.input.google;

import com.mucis.input.CSVParser;
import com.mucis.input.ValidationFilter;
import com.mucis.model.Report;
import com.mucis.repository.ReportRepository;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/upload")
public class GoogleUploadController {

    private static Logger LOG = LoggerFactory.getLogger(GoogleUploadController.class);

    private ReportRepository reportRepository;

    private CSVParser<CsvRecord> csvParser;

    private ValidationFilter<CsvRecord> invalid;

    private ReportAdapter toReport;

    @Autowired
    public GoogleUploadController(
            ReportRepository reportRepository,
            @Qualifier("googleParser") CSVParser<CsvRecord> csvParser,
            ValidationFilter<CsvRecord> invalid,
            @Qualifier("googleAdapter") ReportAdapter toReport) {
        this.reportRepository = reportRepository;
        this.csvParser = csvParser;
        this.invalid = invalid;
        this.toReport = toReport;
    }

    @RequestMapping(method = RequestMethod.POST, value = "google")
    public void handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            csvParser
                .parse(file.getInputStream())
                .filter(invalid)
                .map(toReport)
                .collect(Collectors.groupingBy(Report::getInstance))
                .forEach((report, reports) -> {
                    report.setStreamCount(reports.stream().mapToLong(Report::getStreamCount).sum());
                    report.setDownloadCount(reports.stream().mapToLong(Report::getDownloadCount).sum());
                    reportRepository.insertOrUpdate(report);
                });
        } catch (Exception e) {
            LOG.error("Input data file handling throw an exception.", e);
        }
    }

}
