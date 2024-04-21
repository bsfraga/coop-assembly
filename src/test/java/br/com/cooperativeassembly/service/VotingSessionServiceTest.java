package br.com.cooperativeassembly.service;

import br.com.cooperativeassembly.domain.entity.Agenda;
import br.com.cooperativeassembly.domain.entity.Vote;
import br.com.cooperativeassembly.domain.entity.VotingSession;
import br.com.cooperativeassembly.domain.request.CreateVotingSessionRequest;
import br.com.cooperativeassembly.exception.votingsession.VotingSessionNotFoundException;
import br.com.cooperativeassembly.repository.AgendaRepository;
import br.com.cooperativeassembly.repository.VoteRepository;
import br.com.cooperativeassembly.repository.VotingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class VotingSessionServiceTest {

    @Mock
    private VotingSessionRepository sessionRepository;

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private VotingSessionService votingSessionService;

    @Mock
    private VoteRepository voteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void openSessionSuccess() {
        String agendaId = "agendaId";
        CreateVotingSessionRequest request = CreateVotingSessionRequest.builder()
                .duration(60)
                .build();

        VotingSession session = VotingSession.builder()
                .id("sessionId")
                .agendaId(agendaId)
                .startTime(System.currentTimeMillis())
                .duration(request.getDuration())
                .build();

        when(agendaRepository.findById(agendaId)).thenReturn(Mono.just(Agenda.builder().id(agendaId).build()));
        when(sessionRepository.save(any(VotingSession.class))).thenReturn(Mono.just(session));

        StepVerifier.create(votingSessionService.openSession(agendaId, request))
                .expectNextMatches(sessionDTO -> sessionDTO.getDuration() == request.getDuration() && sessionDTO.getAgendaId().equals(agendaId))
                .verifyComplete();
    }

    @Test
    void openSessionAgendaNotFound() {
        String agendaId = "invalidAgendaId";
        CreateVotingSessionRequest request = CreateVotingSessionRequest.builder()
                .duration(60)
                .build();

        when(agendaRepository.findById(agendaId)).thenReturn(Mono.empty());

        StepVerifier.create(votingSessionService.openSession(agendaId, request))
                .verifyComplete();
    }

    @Test
    void testOpenSessionWithDefaultDuration() {
        String agendaId = "someAgendaId";
        int defaultDuration = 60;

        when(agendaRepository.findById(agendaId)).thenReturn(Mono.just(Agenda.builder().id(agendaId).build()));
        when(sessionRepository.save(any(VotingSession.class))).thenAnswer(invocation -> {
            VotingSession session = invocation.getArgument(0);
            session.setId("someSessionId");
            return Mono.just(session);
        });

        StepVerifier.create(votingSessionService.openSession(agendaId, null))
                .assertNext(sessionDTO -> assertEquals(defaultDuration, sessionDTO.getDuration()))
                .verifyComplete();
    }

    @Test
    void testGetVotingResultForExistingSession() {
        String sessionId = "sessionIdWithVotes";
        when(sessionRepository.findById(sessionId)).thenReturn(Mono.just(VotingSession.builder().id(sessionId).build()));
        when(voteRepository.findAllBySessionId(sessionId)).thenReturn(Flux.just(Vote.builder().decision(true).build()));

        StepVerifier.create(votingSessionService.getVotingResult(sessionId))
                .assertNext(result -> assertTrue(result.getYesVotes() > 0 || result.getNoVotes() > 0))
                .verifyComplete();
    }

    @Test
    void testGetVotingResultForNonExistentSession() {
        String nonExistentSessionId = "nonExistentSessionId";
        when(sessionRepository.findById(nonExistentSessionId)).thenReturn(Mono.empty());

        StepVerifier.create(votingSessionService.getVotingResult(nonExistentSessionId))
                .expectError(VotingSessionNotFoundException.class)
                .verify();
    }
}