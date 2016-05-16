import com.mucis.model.Report
import org.springframework.core.io.FileSystemResource
import org.springframework.data.domain.PageRequest
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class ProcessSpotifyReportSpec extends RestIntegrationBase {

  String getBasePath() {"upload/"}

  def "process spotify report"() {
    given:
    MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
    parts.add("file", new FileSystemResource("./src/integration-test/resources/spotify_streaming_2016_04_02.csv"));

    when:
    restTemplate.postForObject(serviceURI("spotify"), parts, Object.class);

    then:
    List<Report> reports = reportRepository.find(new PageRequest(0, 999))
    reports.size() == 13
    verify(reports.get(0), "2016-04-02", "5055623503266", "GBG5X1204939", 0, 2)
    verify(reports.get(1), "2016-04-02", "0700465407733", "USEAX1100161", 0, 1)
    verify(reports.get(2), "2016-04-02", "8718857025849", "ITR551600078", 0, 1)
  }

}

