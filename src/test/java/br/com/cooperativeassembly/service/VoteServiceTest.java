package br.com.cooperativeassembly.service;

import br.com.cooperativeassembly.domain.dto.VoteDTO;
import br.com.cooperativeassembly.domain.entity.Vote;
import br.com.cooperativeassembly.domain.entity.VotingSession;
import br.com.cooperativeassembly.domain.enums.VotingStatus;
import br.com.cooperativeassembly.domain.request.CastVoteRequest;
import br.com.cooperativeassembly.exception.vote.VoteRegistrationException;
import br.com.cooperativeassembly.exception.vote.VotingSessionClosedException;
import br.com.cooperativeassembly.exception.votingsession.VotingSessionNotFoundException;
import br.com.cooperativeassembly.integration.service.MemberService;
import br.com.cooperativeassembly.repository.VoteRepository;
import br.com.cooperativeassembly.repository.VotingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VotingSessionRepository sessionRepository;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private VoteService voteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void castVoteSuccess() {
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

        when(memberService.isCpfValid("memberId")).thenReturn(Mono.just(true));
        when(sessionRepository.findById("sessionId")).thenReturn(Mono.just(session));
        when(voteRepository.save(any(Vote.class))).thenReturn(Mono.just(vote));
        when(voteRepository.findBySessionIdAndMemberId("sessionId", "memberId")).thenReturn(Mono.empty());

        StepVerifier.create(voteService.castVote("sessionId", CastVoteRequest.builder().memberId("memberId").decision(true).build()))
                .expectNextMatches(voteDTO -> voteDTO.getMemberId().equals(expectedVoteDTO.getMemberId()) &&
                        voteDTO.isDecision() == expectedVoteDTO.isDecision())
                .verifyComplete();
    }

    @Test
    void castVoteInvalidCpf() {
        when(memberService.isCpfValid("invalidCpf")).thenReturn(Mono.just(false));

        StepVerifier.create(voteService.castVote("sessionId", CastVoteRequest.builder().memberId("invalidCpf").decision(true).build()))
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    void castVoteSessionClosed() {
        VotingSession session = VotingSession.builder()
                .id("sessionId")
                .status(VotingStatus.CLOSED)
                .build();

        when(memberService.isCpfValid("memberId")).thenReturn(Mono.just(true));
        when(sessionRepository.findById("sessionId")).thenReturn(Mono.just(session));

        StepVerifier.create(voteService.castVote("sessionId", CastVoteRequest.builder().memberId("memberId").decision(true).build()))
                .expectError(VotingSessionClosedException.class)
                .verify();
    }

    @Test
    void castVoteSessionNotFound() {
        when(memberService.isCpfValid("memberId")).thenReturn(Mono.just(true));
        when(sessionRepository.findById("sessionId")).thenReturn(Mono.empty());

        StepVerifier.create(voteService.castVote("sessionId", CastVoteRequest.builder().memberId("memberId").decision(true).build()))
                .expectError(VotingSessionNotFoundException.class)
                .verify();
    }

    @Test
    void testCastVoteBySameMemberOnSameAgenda() {
        // Arrange
        String sessionId = "someSessionId";
        CastVoteRequest request = CastVoteRequest.builder()
            .memberId("memberIdWhoAlreadyVoted")
            .decision(true)
            .build();

        Vote existingVote = Vote.builder()
            .sessionId(sessionId)
            .memberId("memberIdWhoAlreadyVoted")
            .decision(true)
            .build();

        when(memberService.isCpfValid("memberIdWhoAlreadyVoted")).thenReturn(Mono.just(true));
        when(voteRepository.findBySessionIdAndMemberId(sessionId, "memberIdWhoAlreadyVoted")).thenReturn(Mono.just(existingVote));
        when(sessionRepository.findById(sessionId)).thenReturn(Mono.just(VotingSession.builder().status(VotingStatus.OPEN).build()));

        // Act and Assert
        assertThrows(VoteRegistrationException.class, () -> voteService.castVote(sessionId, request).block());
    }

}
