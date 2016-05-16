package com.mucis.input.google

import javax.validation.Validation
import spock.lang.Specification
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

class CsvRecordSpec extends Specification {

  def validator = Validation.buildDefaultValidatorFactory().getValidator()

  def "get downloads count based on interaction type"() {
    given:
    def record = new CsvRecord()
    record.setCount("10")
    record.setInteraction_Type(type)

    expect:
    record.getDownloadsCount() == downloadCount
    record.getStreamCount() == streamCount

    where:
    type       || downloadCount | streamCount
    "DOWNLOAD" || 10L | 0L
    "STREAM"   || 0L | 10L
  }

  def "validate date"() {
    given:
    def record = createRecord()
    record.setStart_Date(startDate)

    def validator = Validation.buildDefaultValidatorFactory().getValidator()

    when:
    def validate = validator.validate(record)

    then:
    validate.size() == size

    where:
    startDate    || size
    "2015-03-23" || 0
    "aaaa-03-23" || 1
    ""           || 1
  }

  def "validation UPC"() {
    given:
    def record = createRecord()
    record.setUPC(upc)

    when:
    def validate = validator.validate(record)

    then:
    validate.size() == size

    where:
    upc              || size
    "8718403168525"  || 0
    "871840316852"   || 1
    "87184031685212" || 1
  }

  def "validation ISRC"() {
    given:
    def record = createRecord()
    record.setISRC(isrc)

    when:
    def validate = validator.validate(record)

    then:
    validate.size() == size

    where:
    isrc            || size
    "NLA9F1300002"  || 0
    "NLA9F130000"   || 1
    "NLA9F13000021" || 1
  }

  def "validation Interaction Type"() {
    given:
    def record = createRecord()
    record.setInteraction_Type(type)

    when:
    def validate = validator.validate(record)

    then:
    validate.size() == size

    where:
    type                                || size
    "DOWNLOAD"                          || 0
    "STREAM"                            || 0
    "download"                          || 1
    "stream"                            || 1
    "OWNLOAD"                           || 1
    "TREAM"                             || 1
    randomAlphanumeric(7).toUpperCase() || 1
  }

  def "validation count"() {
    given:
    def record = createRecord()
    record.setCount(count)

    when:
    def validate = validator.validate(record)

    then:
    validate.size() == size

    where:
    count         || size
    "1"           || 0
    ""            || 1
    "12345678901" || 1
    "a"           || 1
    "  12"        || 1
  }

  def createRecord() {
    def record = new CsvRecord()
    record.setStart_Date("2015-03-23")
    record.setUPC("8718403168525")
    record.setISRC("NLA9F1300002")
    record.setInteraction_Type("DOWNLOAD")
    record.setCount("9728")
    record
  }

}
