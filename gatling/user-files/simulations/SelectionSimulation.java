import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class SelectionSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .inferHtmlResources()
            .acceptHeader("*/*")
            .acceptEncodingHeader("gzip, deflate, br")
            .acceptLanguageHeader("pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
            .doNotTrackHeader("1")
            .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");

    Map<CharSequence, String> headers_0 = Map.of(
            "Sec-Fetch-Dest", "empty",
            "Sec-Fetch-Mode", "cors",
            "Sec-Fetch-Site", "same-origin",
            "sec-ch-ua", "\"Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123",
            "sec-ch-ua-mobile", "?0",
            "sec-ch-ua-platform", "Windows"
    );

    ScenarioBuilder scn = scenario("SelectionSimulation")
            .exec(
                    http("request_0")
                            .get("/v1/api/selection")
                            .headers(headers_0)
            );

    {
        setUp(
                scn.injectOpen(
                        rampUsers(10).during(Duration.ofMinutes(1)), 
                        rampUsers(1000).during(Duration.ofMinutes(2)) 
                ).protocols(httpProtocol)
        );
    }
}
