package br.com.cooperativeassembly.service;

import br.com.cooperativeassembly.domain.dto.VotingResultDTO;
import br.com.cooperativeassembly.domain.dto.VotingSessionDTO;
import br.com.cooperativeassembly.domain.entity.Vote;
import br.com.cooperativeassembly.domain.entity.VotingSession;
import br.com.cooperativeassembly.domain.request.CreateVotingSessionRequest;
import br.com.cooperativeassembly.exception.votingsession.VotingSessionNotFoundException;
import br.com.cooperativeassembly.repository.AgendaRepository;
import br.com.cooperativeassembly.repository.VoteRepository;
import br.com.cooperativeassembly.repository.VotingSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@AllArgsConstructor
@Service
public class VotingSessionService {

    private final VotingSessionRepository sessionRepository;
    private final VoteRepository voteRepository;
    private final AgendaRepository agendaRepository;

    public Mono<VotingSessionDTO> openSession(String agendaId, CreateVotingSessionRequest request) {
        long duration = (request == null || request.getDuration() <= 0) ? 60L : request.getDuration();
        return agendaRepository.findById(agendaId)
                .flatMap(agenda -> sessionRepository.save(VotingSession.builder()
                        .agendaId(agendaId)
                        .startTime(System.currentTimeMillis())
                        .duration(duration)
                        .build()))
                .flatMap(this::convertToDTO);
    }

    public Mono<VotingResultDTO> getVotingResult(String sessionId) {
        return sessionRepository.findById(sessionId)
                .flatMap(session -> voteRepository.findAllBySessionId(sessionId)
                        .collectList()
                        .map(votes -> VotingResultDTO.builder()
                                .sessionId(sessionId)
                                .yesVotes(votes.stream().filter(Vote::isDecision).count())
                                .noVotes(votes.stream().filter(vote -> !vote.isDecision()).count())
                                .build()))
                .switchIfEmpty(Mono.error(new VotingSessionNotFoundException("Voting session not found with ID: " + sessionId)));
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