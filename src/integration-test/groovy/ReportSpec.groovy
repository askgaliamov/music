import com.mucis.model.Report
import java.text.SimpleDateFormat
import org.springframework.http.ResponseEntity

class ReportSpec extends RestIntegrationBase {

  String getBasePath() {"report/"}

  def "get aggregated report"() {
    given:
    reportRepository.insertOrUpdate(createReport("2015-10-20", "5055876836432", "UK8D41401238", 1, 1))
    reportRepository.insertOrUpdate(createReport("2015-10-20", "5055876836432", "UK8D41401238", 1, 2))
    reportRepository.insertOrUpdate(createReport("2015-10-20", "5055876836433", "UK8D41401238", 1, 2))
    reportRepository.insertOrUpdate(createReport("2015-10-20", "5055876836433", "UK8D41401239", 2, 2))

    when:
    ResponseEntity<Object> responseEntity = restTemplate.getForEntity(serviceURI(), Object.class)
    Object[] objects = responseEntity.getBody()
    println (objects)

    then:
    objects.size() == 3
    verifyy(objects[0], "2015-10-20", "5055876836432", "UK8D41401238", 2L, 3L)
    verifyy(objects[1], "2015-10-20", "5055876836433", "UK8D41401238", 1L, 2L)
    verifyy(objects[2], "2015-10-20", "5055876836433", "UK8D41401239", 2L, 2L)
  }

  def verifyy(Object report, def date, def upc, def isrc, long downloadCount, long streamCount ) {
      report.date == date &&
      report.upc == upc &&
      report.isrc == isrc &&
      report.downloadCount == downloadCount &&
      report.streamCount == streamCount
  }

  def createReport(String date, String upc, String isrc, long downloadCount, long streamCount) {
    new Report(
      new SimpleDateFormat("yyyy-MM-dd").parse(date),
      upc, isrc, downloadCount, streamCount
    )
  }

}

