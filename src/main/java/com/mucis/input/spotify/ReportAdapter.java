package com.mucis.input.spotify;

import com.mucis.model.Report;
import java.util.function.Function;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ReportAdapter implements Function<CsvRecord, Report> {

    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public Report apply(final CsvRecord record) {
        return new Report(
            formatter.parseDateTime(record.getTIMESTAMP()).toDate(),
            record.getALBUM_CODE_UPC(),
            record.getTRACK_ISRC(), 0L, 1L);
    }

}
