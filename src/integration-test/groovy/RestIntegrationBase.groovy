import com.mucis.Application
import com.mucis.model.Report
import com.mucis.repository.ReportRepository
import java.text.SimpleDateFormat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.TestRestTemplate
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Stepwise

@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = [Application.class] )
@WebIntegrationTest
@Stepwise
class RestIntegrationBase extends Specification {
  @Value('${local.server.port}')
  int port

  RestTemplate restTemplate = new TestRestTemplate()

  @Autowired
  ReportRepository reportRepository

  def setup() {
    reportRepository.dropCollection()
  }

  String getBasePath() { "" }

  URI serviceURI(String path = "") {
    new URI("http://localhost:$port/${basePath}${path}")
  }

  def verify(Report report, def date, def upc, def isrc, def downloadCount, def streamCount ) {
    new SimpleDateFormat("yyyy-MM-dd").format(report.date) == date &&
      report.upc == upc &&
      report.isrc == isrc &&
      report.downloadCount == downloadCount &&
      report.streamCount == streamCount
  }

}
