package com.mucis.input.spotify;

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
public class SpotifyUploadController {

    private static Logger LOG = LoggerFactory.getLogger(SpotifyUploadController.class);

    private CSVParser<CsvRecord> csvParser;

    private ValidationFilter<CsvRecord> invalid;

    private ReportAdapter toReport;

    private ReportRepository reportRepository;

    @Autowired
    public SpotifyUploadController(
        @Qualifier("spotifyParser") CSVParser<CsvRecord> csvParser,
        ValidationFilter<CsvRecord> invalid,
        @Qualifier("spotifyAdapter") ReportAdapter toReport,
        ReportRepository reportRepository
    ) {
        this.csvParser = csvParser;
        this.invalid = invalid;
        this.toReport = toReport;
        this.reportRepository = reportRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value = "spotify")
    public void handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            csvParser
                .parse(file.getInputStream())
                .filter(invalid)
                .map(toReport)
                .collect(Collectors.groupingBy(Report::getInstance))
                .forEach((report, reports) -> {
                    report.setStreamCount(reports.stream().mapToLong(Report::getStreamCount).sum());
                    reportRepository.insertOrUpdate(report);
                });
        } catch (Exception e) {
            LOG.error("Input data file handling throw an exception.", e);
        }
    }

}
