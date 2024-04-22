package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.dto.VoteDTO;
import br.com.cooperativeassembly.domain.request.CastVoteRequest;
import br.com.cooperativeassembly.exception.vote.VoteRegistrationException;
import br.com.cooperativeassembly.integration.service.MemberService;
import br.com.cooperativeassembly.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(VoteController.class)
class VoteControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private VoteService voteService;

    @MockBean
    private MemberService memberService;

    @Test
    void castVoteSuccess() {
        String sessionId = "1";
        CastVoteRequest request = CastVoteRequest.builder()
                .memberId("12345678901")
                .decision(TRUE)
                .build();
        VoteDTO vote = VoteDTO.builder()
                .id("1")
                .memberId("12345678901")
                .decision(TRUE)
                .build();

        when(voteService.castVote(sessionId, request)).thenReturn(Mono.just(vote));
        when(memberService.isCpfValid(request.getMemberId())).thenReturn(Mono.just(TRUE));

        webTestClient.post()
                .uri("/vote/" + sessionId)
                .contentType(APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(VoteDTO.class)
                .consumeWith(response -> {
                    VoteDTO voteDTO = response.getResponseBody();
                    assertEquals(voteDTO.getId(), vote.getId());
                    assertEquals(voteDTO.getMemberId(), vote.getMemberId());
                    assertEquals(voteDTO.isDecision(), vote.isDecision());
                });
    }

    @Test
    void castVoteBadRequest() {
        String sessionId = "1";
        CastVoteRequest request = CastVoteRequest.builder()
                .memberId("123456789")
                .decision(TRUE)
                .build();

        when(voteService.castVote(sessionId, request)).thenReturn(Mono.error(new VoteRegistrationException(BAD_REQUEST, "Invalid CPF")));

        webTestClient.post()
                .uri("/vote/" + sessionId)
                .contentType(APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST);
    }
}
