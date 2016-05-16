package com.mucis.input.spotify;

import com.mucis.input.CSVParser;
import com.mucis.input.CSVParserImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpotifyConfig {

    @Bean
    CSVParser spotifyParser() {
        CSVParserImpl<CsvRecord> spotifyUploadControllerCSVParser = new CSVParserImpl<>();
        spotifyUploadControllerCSVParser.setType(CsvRecord.class);
        return spotifyUploadControllerCSVParser;
    }

    @Bean
    ReportAdapter spotifyAdapter() {
        return new ReportAdapter();
    }

}
