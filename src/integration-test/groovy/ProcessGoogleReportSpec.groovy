import com.mucis.model.Report
import org.springframework.core.io.FileSystemResource
import org.springframework.data.domain.PageRequest
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class ProcessGoogleReportSpec extends RestIntegrationBase {

  String getBasePath() {"upload/"}

  def "process google report"() {
    given:
    MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
    parts.add("file", new FileSystemResource("./src/integration-test/resources/GOOGLE_NL_20150323_20150323.csv"));

    when:
    restTemplate.postForObject(serviceURI("google"), parts, Object.class);

    then:
    List<Report> reports = reportRepository.find(new PageRequest(0, 999))
    reports.size() == 3
    verify(reports.get(0), "2015-03-23", "8718403168525", "NLA9F1300002", 9728, 0)
    verify(reports.get(1), "2015-03-23", "5055876808071", "NLRD51436454", 2025, 0)
    verify(reports.get(2), "2015-03-23", "8718857004400", "AUNV01400153", 0, 2286)
  }

}
