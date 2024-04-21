package test.java.br.com.cooperativeassembly.service;

import br.com.cooperativeassembly.domain.dto.VotingSessionDTO;
import br.com.cooperativeassembly.domain.entity.Agenda;
import br.com.cooperativeassembly.domain.entity.VotingSession;
import br.com.cooperativeassembly.domain.request.CreateVotingSessionRequest;
import br.com.cooperativeassembly.repository.AgendaRepository;
import br.com.cooperativeassembly.repository.VotingSessionRepository;
import br.com.cooperativeassembly.service.VotingSessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class VotingSessionServiceTest {

    @Mock
    private VotingSessionRepository sessionRepository;

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private VotingSessionService votingSessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void openSession_Success() {
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
    void openSession_AgendaNotFound() {
        String agendaId = "invalidAgendaId";
        CreateVotingSessionRequest request = CreateVotingSessionRequest.builder()
                .duration(60)
                .build();

        when(agendaRepository.findById(agendaId)).thenReturn(Mono.empty()); // Simula a não existência de uma agenda

        StepVerifier.create(votingSessionService.openSession(agendaId, request))
                .verifyComplete(); // Espera-se que a operação seja concluída com sucesso, mesmo que a agenda não seja encontrada
    }
}