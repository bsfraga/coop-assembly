package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.ErrorResponse;
import br.com.cooperativeassembly.domain.dto.VoteDTO;
import br.com.cooperativeassembly.domain.request.CastVoteRequest;
import br.com.cooperativeassembly.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@Tag(name = "Vote")
@RequestMapping("/vote")
@Slf4j
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/{sessionId}")
    @Operation(summary = "Cast a vote in a voting session",
        description = "Cast a vote in a voting session",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Vote cast",
                            content = @Content(schema = @Schema(implementation = VoteDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Session Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public Mono<ResponseEntity<VoteDTO>> castVote(@PathVariable String sessionId, @RequestBody CastVoteRequest request) {
        return voteService.castVote(sessionId, request)
                .doOnSuccess(vote -> log.info("Vote cast successfully in session: {}", sessionId))
                .doOnError(error -> log.error("Error occurred while casting vote in session: {}", sessionId, error))
                .map(vote -> ResponseEntity.created(URI.create("/vote/" + sessionId)).body(vote));
    }
}
