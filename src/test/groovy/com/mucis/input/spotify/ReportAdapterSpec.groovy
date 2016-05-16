package com.mucis.input.spotify

import spock.lang.Specification

class ReportAdapterSpec extends Specification {

  def "transform CsvRecord to Report"() {
    given:
    def record = new CsvRecord()
    record.setTIMESTAMP("20160402T00:39:00")
    record.setALBUM_CODE_UPC("123")
    record.setTRACK_ISRC("456")

    when:
    def report = new ReportAdapter().apply(record)

    then:
    report.date.getTime() == 1459548000000L
    report.upc == "123"
    report.isrc == "456"
    report.downloadCount == 0L
    report.streamCount == 1L
  }

}
