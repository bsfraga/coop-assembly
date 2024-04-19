package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.dto.VoteDTO;
import br.com.cooperativeassembly.domain.request.CastVoteRequest;
import br.com.cooperativeassembly.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/votes")
@Tag(name = "Vote")
@Slf4j
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/{sessionId}")
    @Operation(summary = "Cast a vote on a session")
    public Mono<ResponseEntity<VoteDTO>> castVote(@PathVariable String sessionId, @RequestBody CastVoteRequest request) {
        return voteService.castVote(sessionId, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
