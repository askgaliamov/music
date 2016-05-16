package com.mucis.input.google

import spock.lang.Specification


class ReportAdapterSpec extends Specification {

  def "transform CsvRecord to Report"() {
    given:
    def record = new CsvRecord()
    record.setStart_Date("2016-04-02")
    record.setInteraction_Type("DOWNLOAD")
    record.setUPC("123")
    record.setISRC("AL09")
    record.setCount("23")

    when:
    def report = new ReportAdapter().apply(record)

    then:
    report.date.getTime() == 1459548000000L
    report.upc == "123"
    report.isrc == "AL09"
    report.downloadCount == 23L
    report.streamCount == 0L
  }

}
