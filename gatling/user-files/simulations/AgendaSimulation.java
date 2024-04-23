import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.util.Map;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AgendaSimulation extends Simulation {

  HttpProtocolBuilder httpProtocol = http
          .baseUrl("http://localhost:8080/v1")
          .acceptHeader("*/*")
          .acceptEncodingHeader("gzip, deflate, br")
          .acceptLanguageHeader("pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
          .doNotTrackHeader("1")
          .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");

  Map<CharSequence, String> commonHeaders = Map.ofEntries(
          Map.entry("Sec-Fetch-Dest", "empty"),
          Map.entry("Sec-Fetch-Mode", "cors"),
          Map.entry("Sec-Fetch-Site", "same-origin"),
          Map.entry("sec-ch-ua", "\"Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123"),
          Map.entry("sec-ch-ua-mobile", "?0"),
          Map.entry("sec-ch-ua-platform", "Windows")
  );

  Map<CharSequence, String> jsonHeaders = Map.ofEntries(
          Map.entry("Content-Type", "application/json"),
          Map.entry("Origin", "http://localhost:8080")
  );

  ScenarioBuilder browseAgendas = scenario("Browse Agendas")
          .exec(http("Get All Agendas")
                  .get("/api/agendas")
                  .headers(commonHeaders))
          .pause(1);

  ScenarioBuilder postAgenda = scenario("Post Agenda")
          .exec(http("Create Agenda")
                  .post("/api/agendas")
                  .headers(jsonHeaders)
                  .body(StringBody(session -> {
                    String randomString = "description_" + session.userId();
                    return "{\"description\": \"6622c99e6a15863632dd487f\"}";
                  })))
          .pause(2);

  ScenarioBuilder getAgendaById = scenario("Get Agenda by ID")
          .exec(http("Get Specific Agenda")
                  .get("/api/agendas/6622c99e6a15863632dd487f")
                  .headers(commonHeaders))
          .pause(1);

  {
    setUp(
            browseAgendas.injectOpen(rampUsers(10).during(10)),
            postAgenda.injectOpen(rampUsers(100).during(20)),
            getAgendaById.injectOpen(atOnceUsers(50))
    ).protocols(httpProtocol);
  }
}
