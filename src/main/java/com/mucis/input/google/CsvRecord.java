package com.mucis.input.google;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvRecord {

    private static Logger LOG = LoggerFactory.getLogger(GoogleUploadController.class);

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String Start_Date;
    @Size(min = 13)
    @Digits(integer = 13, fraction = 0)
    private String UPC;
    @Size(min = 12, max = 12)
    private String ISRC;
    @Pattern(regexp = "(STREAM|DOWNLOAD)")
    private String Interaction_Type;
    @Digits(integer = 10, fraction = 0)
    private String Count;

    public void setStart_Date(final String start_Date) {
        try {
            this.Start_Date = dateFormat.format(dateFormat.parse(start_Date));
        } catch (ParseException e) {
            this.Start_Date = "";
            LOG.warn("Invalid date: [{}]", start_Date);
        }
    }

    public void setUPC(final String UPC) {
        this.UPC = UPC;
    }

    public void setISRC(final String ISRC) {
        this.ISRC = ISRC;
    }

    public void setInteraction_Type(final String interaction_Type) {
        Interaction_Type = interaction_Type;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getStart_Date() {
        return Start_Date;
    }

    public String getUPC() {
        return UPC;
    }

    public String getISRC() {
        return ISRC;
    }

    public String getInteraction_Type() {
        return Interaction_Type;
    }

    public Long getDownloadsCount() {
        return "DOWNLOAD".equals(Interaction_Type) ? Long.parseLong(Count) : 0L;
    }

    public Long getStreamCount() {
        return "STREAM".equals(Interaction_Type) ? Long.parseLong(Count) : 0L;
    }

    @Override
    public String toString() {
        return Start_Date + ',' +
            UPC + ',' +
            ISRC + ',' +
            Interaction_Type + ',' +
            Count;
    }
}
