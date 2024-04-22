package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.ErrorResponse;
import br.com.cooperativeassembly.domain.dto.VotingResultDTO;
import br.com.cooperativeassembly.domain.dto.VotingSessionDTO;
import br.com.cooperativeassembly.domain.request.CreateVotingSessionRequest;
import br.com.cooperativeassembly.service.VotingSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/sessions")
@Tag(name = "Voting Session")
@Slf4j
@AllArgsConstructor
public class VotingSessionController {

    private final VotingSessionService sessionService;

    @PostMapping("/{agendaId}")
    @Operation(summary = "Open a voting session for an agenda",
        description = "Open a voting session for an agenda with the provided duration",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Voting session opened",
                            content = @Content(schema = @Schema(implementation = VotingSessionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Agenda Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public Mono<ResponseEntity<VotingSessionDTO>> openSession(@PathVariable String agendaId, @RequestBody CreateVotingSessionRequest request, UriComponentsBuilder uriBuilder) {
        log.info("Opening voting session for agenda: {}", agendaId);
        return sessionService.openSession(agendaId, request)
                .doOnSuccess(session -> log.info("Voting session opened successfully: {}", session))
                .doOnError(error -> log.error("Error occurred while opening voting session for agenda: {}", agendaId, error))
                .map(session -> {
                    URI location = uriBuilder.path("/api/sessions/{id}")
                            .buildAndExpand(session.getId())
                            .toUri();
                    return ResponseEntity.created(location).body(session);
                });
    }

    @GetMapping("/result/{sessionId}")
    @Operation(summary = "Get the voting result for a session",
        description = "Get the voting result for a session",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Voting result retrieved",
                            content = @Content(schema = @Schema(implementation = VotingResultDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Session Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public Mono<ResponseEntity<VotingResultDTO>> getVotingResult(@PathVariable String sessionId) {
        log.info("Getting voting result for session: {}", sessionId);
        return sessionService.getVotingResult(sessionId)
                .doOnSuccess(result -> log.info("Voting result retrieved successfully: {}", result))
                .doOnError(error -> log.error("Error occurred while getting voting result for session: {}", sessionId, error))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}