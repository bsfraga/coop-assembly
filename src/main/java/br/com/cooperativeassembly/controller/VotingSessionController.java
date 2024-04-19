package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.dto.VotingSessionDTO;
import br.com.cooperativeassembly.domain.request.CreateVotingSessionRequest;
import br.com.cooperativeassembly.service.VotingSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/sessions")
public class VotingSessionController {

    private final VotingSessionService sessionService;

    public VotingSessionController(VotingSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/{agendaId}")
    public Mono<ResponseEntity<VotingSessionDTO>> openSession(@PathVariable String agendaId, @RequestBody CreateVotingSessionRequest request) {
        return sessionService.openSession(agendaId, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}