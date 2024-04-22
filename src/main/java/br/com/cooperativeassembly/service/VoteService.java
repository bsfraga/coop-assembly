package br.com.cooperativeassembly.service;

import br.com.cooperativeassembly.domain.dto.VoteDTO;
import br.com.cooperativeassembly.domain.entity.Vote;
import br.com.cooperativeassembly.domain.enums.VotingStatus;
import br.com.cooperativeassembly.domain.request.CastVoteRequest;
import br.com.cooperativeassembly.exception.vote.VoteRegistrationException;
import br.com.cooperativeassembly.exception.vote.VotingSessionClosedException;
import br.com.cooperativeassembly.exception.votingsession.VotingSessionNotFoundException;
import br.com.cooperativeassembly.integration.service.MemberService;
import br.com.cooperativeassembly.repository.VoteRepository;
import br.com.cooperativeassembly.repository.VotingSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static java.lang.Boolean.FALSE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VotingSessionRepository sessionRepository;
    private final MemberService memberService;

    public Mono<VoteDTO> castVote(String sessionId, CastVoteRequest request) {
        return memberService.isCpfValid(request.getMemberId())
                .flatMap(isValid -> {
                    if (FALSE.equals(isValid)) {
                        throw new VoteRegistrationException(BAD_REQUEST, "Invalid CPF");
                    }
                    return sessionRepository.findById(sessionId);
                })
                .flatMap(session -> {
                    if (VotingStatus.CLOSED.equals(session.getStatus())) {
                        throw new VotingSessionClosedException("Voting session is closed");
                    }
                    return voteRepository.findBySessionIdAndMemberId(sessionId, request.getMemberId())
                            .hasElement()
                            .flatMap(exists -> {
                                if (Boolean.TRUE.equals(exists)) {
                                    throw new VoteRegistrationException(BAD_REQUEST, "Member has already voted in this session");
                                }
                                return voteRepository.save(Vote.builder()
                                        .sessionId(sessionId)
                                        .memberId(request.getMemberId())
                                        .decision(request.getDecision())
                                        .build());
                            });
                })
                .map(this::convertToDTO)
                .switchIfEmpty(Mono.error(new VotingSessionNotFoundException("Voting session not found with ID: " + sessionId)));
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