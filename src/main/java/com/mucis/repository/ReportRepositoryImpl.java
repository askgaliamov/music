package com.mucis.repository;

import com.mucis.model.Report;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ReportRepositoryImpl implements ReportRepository {

    @Autowired
    private MongoTemplate db;

    public int insertOrUpdate(Report report) {
        Query query = new Query().addCriteria(
            Criteria
                .where(DATE_FIELD).is(report.getDate())
                .and(UPC_FIELD).is(report.getUpc())
                .and(ISRC_FIELD).is(report.getIsrc()));

        Update update = new Update()
            .inc(DOWNLOAD_COUNT_FILED, report.getDownloadCount())
            .inc(STREAM_COUNT_FIELD, report.getStreamCount());

        return db.upsert(query, update, Report.class).getN();
    }

    public List<Report> find(Pageable pageable) {
        Query query = new Query();
        query.with(pageable);
        return db.find(query, Report.class);
    }

    public List<Report> find(Date date, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(new Criteria(DATE_FIELD).is(date));
        query.with(pageable);
        return db.find(query, Report.class);
    }

    public List<Report> findByUPC(String upc, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(new Criteria(UPC_FIELD).is(upc));
        query.with(pageable);
        return db.find(query, Report.class);
    }

    public List<Report> findByISRC(String isrc, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(new Criteria(ISRC_FIELD).is(isrc));
        query.with(pageable);
        return db.find(query, Report.class);
    }

    public void dropCollection() {
        db.dropCollection(Report.class);
    }

}
