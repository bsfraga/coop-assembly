package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.dto.VotingResultDTO;
import br.com.cooperativeassembly.domain.dto.VotingSessionDTO;
import br.com.cooperativeassembly.domain.request.CreateVotingSessionRequest;
import br.com.cooperativeassembly.service.VotingSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/sessions")
@Tag(name = "Voting Session")
@Slf4j
@AllArgsConstructor
public class VotingSessionController {

    private final VotingSessionService sessionService;

    @PostMapping("/{agendaId}")
    @Operation(summary = "Open a voting session for an agenda")
    public Mono<ResponseEntity<VotingSessionDTO>> openSession(@PathVariable String agendaId, @RequestBody CreateVotingSessionRequest request) {
        return sessionService.openSession(agendaId, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/result/{sessionId}")
    @Operation(summary = "Get the voting result for a session")
    public Mono<ResponseEntity<VotingResultDTO>> getVotingResult(@PathVariable String sessionId) {
        return sessionService.getVotingResult(sessionId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}