package test.java.br.com.cooperativeassembly.service;

import br.com.cooperativeassembly.domain.dto.VoteDTO;
import br.com.cooperativeassembly.domain.entity.Vote;
import br.com.cooperativeassembly.domain.entity.VotingSession;
import br.com.cooperativeassembly.domain.enums.VotingStatus;
import br.com.cooperativeassembly.domain.request.CastVoteRequest;
import br.com.cooperativeassembly.exception.vote.VotingSessionClosedException;
import br.com.cooperativeassembly.exception.votingsession.VotingSessionNotFoundException;
import br.com.cooperativeassembly.repository.VoteRepository;
import br.com.cooperativeassembly.repository.VotingSessionRepository;
import br.com.cooperativeassembly.service.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VotingSessionRepository sessionRepository;

    @InjectMocks
    private VoteService voteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void castVote_Success() {
        VotingSession session = VotingSession.builder()
                .id("sessionId")
                .status(VotingStatus.OPEN)
                .build();

        Vote vote = Vote.builder()
                .sessionId("sessionId")
                .memberId("memberId")
                .decision(true)
                .build();

        VoteDTO expectedVoteDTO = VoteDTO.builder()
                .id("voteId")
                .sessionId("sessionId")
                .memberId("memberId")
                .decision(true)
                .build();

        when(sessionRepository.findById("sessionId")).thenReturn(Mono.just(session));
        when(voteRepository.save(any(Vote.class))).thenReturn(Mono.just(vote));

        StepVerifier.create(voteService.castVote("sessionId", CastVoteRequest.builder().memberId("memberId").decision(true).build()))
                .expectNextMatches(voteDTO -> voteDTO.getMemberId().equals(expectedVoteDTO.getMemberId()) &&
                        voteDTO.isDecision() == expectedVoteDTO.isDecision())
                .verifyComplete();
    }

    @Test
    void castVote_SessionClosed() {
        VotingSession session = VotingSession.builder()
                .id("sessionId")
                .status(VotingStatus.CLOSED)
                .build();

        when(sessionRepository.findById("sessionId")).thenReturn(Mono.just(session));

        StepVerifier.create(voteService.castVote("sessionId", CastVoteRequest.builder().memberId("memberId").decision(true).build()))
                .expectError(VotingSessionClosedException.class)
                .verify();
    }

    @Test
    void castVote_SessionNotFound() {
        when(sessionRepository.findById("sessionId")).thenReturn(Mono.empty());

        StepVerifier.create(voteService.castVote("sessionId", CastVoteRequest.builder().memberId("memberId").decision(true).build()))
                .expectError(VotingSessionNotFoundException.class)
                .verify();
    }
}