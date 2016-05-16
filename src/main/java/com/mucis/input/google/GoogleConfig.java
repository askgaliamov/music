package com.mucis.input.google;

import com.mucis.input.CSVParser;
import com.mucis.input.CSVParserImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleConfig {

    @Bean
    CSVParser googleParser() {
        CSVParserImpl<CsvRecord> googleCsvRecordCSVParser = new CSVParserImpl<>();
        googleCsvRecordCSVParser.setSkilPline(2);
        googleCsvRecordCSVParser.setType(CsvRecord.class);
        return googleCsvRecordCSVParser;
    }

    @Bean
    ReportAdapter googleAdapter() {
        return new ReportAdapter();
    }

}
