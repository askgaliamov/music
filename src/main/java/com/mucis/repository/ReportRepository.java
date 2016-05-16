package com.mucis.repository;

import com.mucis.model.Report;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ReportRepository {

    String DATE_FIELD = "date";
    String UPC_FIELD = "upc";
    String ISRC_FIELD = "isrc";
    String DOWNLOAD_COUNT_FILED = "downloadCount";
    String STREAM_COUNT_FIELD = "streamCount";

    int insertOrUpdate(Report report);

    List<Report> find(Pageable pageable);

    List<Report> find(Date date, Pageable pageable);

    List<Report> findByUPC(String upc, Pageable pageable);

    List<Report> findByISRC(String isrc, Pageable pageable);

    void dropCollection();

}
