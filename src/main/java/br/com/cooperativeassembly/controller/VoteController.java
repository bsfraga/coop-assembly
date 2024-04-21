package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.dto.VoteDTO;
import br.com.cooperativeassembly.domain.request.CastVoteRequest;
import br.com.cooperativeassembly.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/votes")
@Tag(name = "Vote")
@Slf4j
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/{sessionId}")
    @Operation(summary = "Cast a vote on a session")
    public Mono<ResponseEntity<VoteDTO>> castVote(@PathVariable String sessionId, @RequestBody CastVoteRequest request) {
        log.info("Received a request to cast a vote in session: {}", sessionId);
        return voteService.castVote(sessionId, request)
                .doOnSuccess(vote -> log.info("Vote cast successfully in session: {}", sessionId))
                .doOnError(error -> log.error("Error occurred while casting vote in session: {}", sessionId, error))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
