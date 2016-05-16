package com.mucis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.Objects;
import org.springframework.data.annotation.Id;

public class Report {

    @Id
    private String id;

    private Date date;
    private String upc;
    private String isrc;
    private Long downloadCount;
    private Long streamCount;

    public Report() {
    }

    public Report(final Date date, final String upc, final String isrc, final Long downloadCount, Long streamCount) {
        this.date = date;
        this.upc = upc;
        this.isrc = isrc;
        this.downloadCount = downloadCount;
        this.streamCount = streamCount;
    }

    @JsonFormat(pattern="yyyy-MM-dd", timezone="Asia/Beirut")
    public Date getDate() {
        return date;
    }

    public String getUpc() {
        return upc;
    }

    public String getIsrc() {
        return isrc;
    }

    public void incrDownloadCount() {
        downloadCount++;
    }

    public void incrStreamCount() {
        downloadCount++;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public Long getStreamCount() {
        return streamCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, upc, isrc);
    }

    @JsonIgnore
    public Report getInstance() {
        return this;
    }

    public void setDownloadCount(final Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    public void setStreamCount(final Long streamCount) {
        this.streamCount = streamCount;
    }

    @Override
    public String toString() {
        return "Report{" +
            "id='" + id + '\'' +
            ", date=" + date +
            ", upc='" + upc + '\'' +
            ", isrc='" + isrc + '\'' +
            ", downloadCount=" + downloadCount +
            ", streamCount=" + streamCount +
            "}\n";
    }
}
