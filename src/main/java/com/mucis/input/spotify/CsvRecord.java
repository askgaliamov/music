package com.mucis.input.spotify;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import static org.eclipse.jetty.io.SelectChannelEndPoint.LOG;
import org.hibernate.validator.constraints.NotEmpty;

public class CsvRecord {

    private static final DateFormat spotify = new SimpleDateFormat("yyyyMMdd'T'");
    private static final DateFormat our = new SimpleDateFormat("yyyy-MM-dd");

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String TIMESTAMP;
    @NotEmpty
    @Digits(integer = 13, fraction = 0)
    private String ALBUM_CODE_UPC;
    @NotEmpty
    @Size(min = 12, max = 12)
    private String TRACK_ISRC;

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(final String TIMESTAMP) {
        try {
            this.TIMESTAMP = our.format(spotify.parse(TIMESTAMP));
        } catch (ParseException e) {
            this.TIMESTAMP = "";
            LOG.warn("Invalid date: [{}]", TIMESTAMP);
        }
    }

    public String getALBUM_CODE_UPC() {
        return ALBUM_CODE_UPC;
    }

    public void setALBUM_CODE_UPC(final String ALBUM_CODE_UPC) {
        this.ALBUM_CODE_UPC = ALBUM_CODE_UPC;
    }

    public String getTRACK_ISRC() {
        return TRACK_ISRC;
    }

    public void setTRACK_ISRC(final String TRACK_ISRC) {
        this.TRACK_ISRC = TRACK_ISRC;
    }

    @Override
    public String toString() {
        return TIMESTAMP + ',' +
            ALBUM_CODE_UPC + ',' +
            TRACK_ISRC;
    }
}
