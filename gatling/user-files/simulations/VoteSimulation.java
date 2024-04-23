import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;
import java.util.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class VoteSimulation extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080/v1/api")
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate, br")
        .acceptLanguageHeader("pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
        .doNotTrackHeader("1")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");

    private Map<CharSequence, String> commonHeaders = Map.ofEntries(
        Map.entry("Sec-Fetch-Dest", "empty"),
        Map.entry("Sec-Fetch-Mode", "cors"),
        Map.entry("Sec-Fetch-Site", "same-origin"),
        Map.entry("sec-ch-ua", "\"Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123"),
        Map.entry("sec-ch-ua-mobile", "?0"),
        Map.entry("sec-ch-ua-platform", "Windows"));

        private Map<CharSequence, String> jsonHeaders = Map.ofEntries(
            Map.entry("Content-Type", "application/json"),
            Map.entry("Origin", "http://localhost:8080")
        );

    private ScenarioBuilder scn = scenario("VoteSimulation")
        .exec(http("List Agendas")
            .get("/agendas")
            .headers(commonHeaders)
            .check(jsonPath("$[*].id").findAll().saveAs("agendaIds")))
        .exec(session -> {
            List<String> agendaIds = session.getList("agendaIds");
            if (agendaIds == null || agendaIds.isEmpty()) {
                return session.markAsFailed();
            }
            String agendaId = agendaIds.get(0);
            return session.set("agendaId", agendaId);
        })
        .exec(http("Open Voting Session")
            .post("/sessions/#{agendaId}")
            .headers(jsonHeaders)
            .body(StringBody("{\"duration\": \"5\"}"))
            .check(jsonPath("$.id").saveAs("sessionId")))
        .exec(http("Cast Vote")
            .post("/vote/#{sessionId}")
            .headers(jsonHeaders)
            .body(StringBody("{\"memberId\": \"" + generateValidCpf() + "\", \"decision\": \"true\"}"))
            .check(status().is(201)))
        .exec(http("Get Voting Results")
            .get("/sessions/result/#{sessionId}")
            .headers(commonHeaders));

    {
        setUp(scn.injectOpen(
            constantUsersPerSec(100).during(Duration.ofMinutes(5))
        )).protocols(httpProtocol);
    }

    private String generateValidCpf() {
        Random random = new Random();
        int[] cpf = new int[9];
        StringBuilder cpfString = new StringBuilder();
        for (int i = 0; i < cpf.length; i++) {
            cpf[i] = random.nextInt(10);
            cpfString.append(cpf[i]);
        }

        int sum = 0;
        for (int i = 0; i < cpf.length; i++) {
            sum += cpf[i] * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        cpfString.append(firstDigit > 9 ? 0 : firstDigit);

        sum = 0;
        for (int i = 0; i < cpf.length; i++) {
            sum += cpf[i] * (11 - i);
        }
        sum += firstDigit * 2;
        int secondDigit = 11 - (sum % 11);
        cpfString.append(secondDigit > 9 ? 0 : secondDigit);

        return cpfString.toString();
    }
}
