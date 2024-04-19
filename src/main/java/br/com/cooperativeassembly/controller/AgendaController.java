package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.dto.AgendaDTO;
import br.com.cooperativeassembly.domain.request.CreateAgendaRequest;
import br.com.cooperativeassembly.service.AgendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping
    public Mono<ResponseEntity<AgendaDTO>> createAgenda(@RequestBody CreateAgendaRequest request) {
        return agendaService.createAgenda(request)
                .map(savedAgenda -> ResponseEntity.ok(savedAgenda))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public Flux<AgendaDTO> getAllAgendas() {
        return agendaService.getAllAgendas();
    }
}
