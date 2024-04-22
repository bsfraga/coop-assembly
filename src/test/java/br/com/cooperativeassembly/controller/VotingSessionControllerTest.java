package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.dto.VotingResultDTO;
import br.com.cooperativeassembly.domain.dto.VotingSessionDTO;
import br.com.cooperativeassembly.domain.entity.Agenda;
import br.com.cooperativeassembly.domain.request.CreateVotingSessionRequest;
import br.com.cooperativeassembly.exception.votingsession.VotingSessionNotFoundException;
import br.com.cooperativeassembly.repository.AgendaRepository;
import br.com.cooperativeassembly.service.VotingSessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(VotingSessionController.class)
class VotingSessionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private VotingSessionService votingSessionService;

    @MockBean
    private AgendaRepository agendaRepository;

    @Test
    void createVotingSessionTest() {
        String agendaId = "1";
        CreateVotingSessionRequest request = CreateVotingSessionRequest.builder().duration(1L).build();
        VotingSessionDTO session = VotingSessionDTO.builder()
                .id("1")
                .agendaId(agendaId)
                .startTime(LocalDateTime.now())
                .duration(1L)
                .build();
        Agenda agenda = Agenda.builder().id(agendaId).build();

        when(votingSessionService.openSession(eq(agendaId), any())).thenReturn(Mono.just(session));
        when(agendaRepository.findById(agendaId)).thenReturn(Mono.just(agenda));

        webTestClient.post()
                .uri("/sessions/" + agendaId)
                .contentType(APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(VotingSessionDTO.class);
    }

    @Test
    void openSessionError() {
        String agendaId = "1";
        CreateVotingSessionRequest request = CreateVotingSessionRequest.builder().duration(60L).build();

        when(votingSessionService.openSession(agendaId, request)).thenReturn(Mono.error(new VotingSessionNotFoundException("Error occurred")));

        webTestClient.post()
                .uri("/sessions/" + agendaId)
                .contentType(APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getVotingResultSuccess() {
        String sessionId = "1";
        VotingResultDTO result = new VotingResultDTO(sessionId, 10L, 5L);
        Agenda agenda = Agenda.builder().id("1").build();

        when(votingSessionService.getVotingResult(sessionId)).thenReturn(Mono.just(result));
        when(agendaRepository.findById(anyString())).thenReturn(Mono.just(agenda));

        webTestClient.get()
                .uri("/sessions/result/" + sessionId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(VotingResultDTO.class);
    }

    @Test
    void getVotingResultError() {
        String sessionId = "1";

        when(votingSessionService.getVotingResult(sessionId)).thenReturn(Mono.error(new VotingSessionNotFoundException("Error occurred")));

        webTestClient.get()
                .uri("/sessions/result/" + sessionId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }
}