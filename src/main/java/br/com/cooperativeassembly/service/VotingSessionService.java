package br.com.cooperativeassembly.service;

import br.com.cooperativeassembly.domain.dto.VotingSessionDTO;
import br.com.cooperativeassembly.domain.request.CreateVotingSessionRequest;
import br.com.cooperativeassembly.repository.AgendaRepository;
import br.com.cooperativeassembly.repository.VotingSessionRepository;
import br.com.cooperativeassembly.domain.entity.VotingSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class VotingSessionService {

    private final VotingSessionRepository sessionRepository;
    private final AgendaRepository agendaRepository;

    public Mono<VotingSessionDTO> openSession(String agendaId, CreateVotingSessionRequest request) {
        return agendaRepository.findById(agendaId)
                .flatMap(agenda -> sessionRepository.save(VotingSession.builder()
                        .agendaId(agendaId)
                        .startTime(System.currentTimeMillis())
                        .duration(request.getDuration())
                        .build()))
                .flatMap(this::convertToDTO);
    }

    private Mono<VotingSessionDTO> convertToDTO(VotingSession session) {
        return Mono.just(VotingSessionDTO.builder()
                .id(session.getId())
                .agendaId(session.getAgendaId())
                .startTime(session.getStartTime())
                .duration(session.getDuration())
                .build());
    }
}