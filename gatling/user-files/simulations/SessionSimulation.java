import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.util.Map;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class SessionSimulation extends Simulation {

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

    ScenarioBuilder scn = scenario("VotingSessionFlow")
            .exec(http("List Agendas")
                    .get("/api/agendas")
                    .headers(commonHeaders)
                    .check(jsonPath("$[*].id").findAll().saveAs("agendaIds"))
            )
            .pause(2)
            .exec(session -> {
                var agendaIds = session.<java.util.List<String>>get("agendaIds");
                var randomId = agendaIds.get(new java.util.Random().nextInt(agendaIds.size()));
                return session.set("randomAgendaId", randomId);
            })
            .exec(http("Create Voting Session")
                    .post("/api/sessions/${randomAgendaId}")
                    .headers(jsonHeaders)
                    .body(StringBody("{\"duration\":\"5\"}"))
                    .check(jsonPath("$.id").saveAs("sessionId"))
            )
            .pause(1)
            .exec(http("Get Voting Results")
                    .get("/api/sessions/result/${sessionId}")
                    .headers(commonHeaders)
            );

    {
        setUp(scn.injectOpen(rampUsers(50).during(300))).protocols(httpProtocol);
    }
}