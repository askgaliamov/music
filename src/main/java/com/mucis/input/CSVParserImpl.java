package com.mucis.input;

import com.opencsv.CSVReader;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.IterableCSVToBean;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CSVParserImpl<T>  implements CSVParser<T> {

    private static final char SEPARATOR = ',';
    private static final char QUOTECHAR = '"';

    private Class<T> type;
    private int skilPline;

    @Override
    public Stream<T> parse(final InputStream inputStream) {
        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(type);
        InputStreamReader isr = new InputStreamReader(inputStream);
        CSVReader reader = new CSVReader(isr, SEPARATOR, QUOTECHAR, skilPline);
        IterableCSVToBean<T> googleCsvRecords = new IterableCSVToBean<>(reader, strategy, null);
        return StreamSupport.stream(googleCsvRecords.spliterator(), false);
    }

    public void setSkilPline(final int skilPline) {
        this.skilPline = skilPline;
    }

    public void setType(final Class<T> type) {
        this.type = type;
    }
}
