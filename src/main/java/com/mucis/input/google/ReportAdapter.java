package com.mucis.input.google;

import com.mucis.model.Report;
import java.util.function.Function;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ReportAdapter implements Function<CsvRecord, Report> {

    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public Report apply(final CsvRecord record) {
        return new Report(
            formatter.parseDateTime(record.getStart_Date()).toDate(),
            record.getUPC(),
            record.getISRC(),
            record.getDownloadsCount(),
            record.getStreamCount()
        );
    }
}
