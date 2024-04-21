package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.dto.AgendaDTO;
import br.com.cooperativeassembly.domain.request.CreateAgendaRequest;
import br.com.cooperativeassembly.service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/agendas")
@Tag(name = "Agenda")
@Slf4j
@AllArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    @PostMapping
    @Operation(summary = "Create an agenda")
    public Mono<ResponseEntity<AgendaDTO>> createAgenda(@RequestBody CreateAgendaRequest request) {
        log.info("Creating agenda with request: {}", request);
        return agendaService.createAgenda(request)
                .doOnNext(agenda -> log.info("Created agenda: {}", agenda))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping
    @Operation(summary = "Get all agendas")
    public Flux<AgendaDTO> getAllAgendas() {
        log.info("Getting all agendas");
        return agendaService.getAllAgendas()
                .doOnNext(agenda -> log.info("Retrieved agenda: {}", agenda));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an agenda by id")
    public Mono<ResponseEntity<AgendaDTO>> getAgendaById(@PathVariable String id) {
        log.info("Getting agenda with id: {}", id);
        return agendaService.getAgendaById(id)
                .doOnNext(agenda -> log.info("Retrieved agenda: {}", agenda))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
