package br.com.cooperativeassembly.service;

import br.com.cooperativeassembly.domain.dto.VoteDTO;
import br.com.cooperativeassembly.domain.request.CastVoteRequest;
import br.com.cooperativeassembly.repository.VoteRepository;
import br.com.cooperativeassembly.repository.VotingSessionRepository;
import br.com.cooperativeassembly.domain.entity.Vote;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VotingSessionRepository sessionRepository;

    public Mono<VoteDTO> castVote(String sessionId, CastVoteRequest request) {
        return sessionRepository.findById(sessionId)
                .flatMap(session -> voteRepository.save(Vote.builder()
                        .sessionId(sessionId)
                        .memberId(request.getMemberId())
                        .decision(request.isDecision())
                        .build()))
                .map(this::convertToDTO)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Voting session not found with ID: " + sessionId)));
    }

    private VoteDTO convertToDTO(Vote vote) {
        return VoteDTO.builder()
                .id(vote.getId())
                .sessionId(vote.getSessionId())
                .memberId(vote.getMemberId())
                .decision(vote.isDecision())
                .build();
    }
}