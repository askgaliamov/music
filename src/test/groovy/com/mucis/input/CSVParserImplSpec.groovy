package com.mucis.input

import java.util.stream.Stream
import spock.lang.Specification

class CSVParserImplSpec extends Specification {

  def "parse simple record"() {
    given:
    def csv = "field1,field2,field3\n" +
              "123,456,\"a b, c\""
    CSVParser parser = new CSVParserImpl<CSVRecord>()
    parser.setType(CSVRecord.class)

    when:
    Stream<CSVRecord> parse = parser.parse(new ByteArrayInputStream(csv.getBytes()))

    then:
    def bean = parse.findFirst().get()
    bean.field1 == "123"
    bean.field2 == "456"
    bean.field3 == "a b, c"
  }

  def "skip first two lines"() {
    given:
    def csv = "fielda,fieldb,fieldc\n" +
              "1,2,a\n" +
              "field1,field2,field3\n" +
              "123,456,\"a b, c\""
    CSVParser parser = new CSVParserImpl<CSVRecord>()
    parser.setType(CSVRecord.class)
    parser.setSkilPline(2)

    when:
    Stream<CSVRecord> parse = parser.parse(new ByteArrayInputStream(csv.getBytes()))

    then:
    def bean = parse.findFirst().get()
    bean.field1 == "123"
    bean.field2 == "456"
    bean.field3 == "a b, c"
  }


}
