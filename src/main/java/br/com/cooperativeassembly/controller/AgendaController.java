package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.ErrorResponse;
import br.com.cooperativeassembly.domain.dto.AgendaDTO;
import br.com.cooperativeassembly.domain.request.CreateAgendaRequest;
import br.com.cooperativeassembly.service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/agendas")
@Tag(name = "Agenda")
@Slf4j
@AllArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    @PostMapping
    @Operation(summary = "Create an agenda",
            description = "Creates an agenda with the provided title and description",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Agenda created",
                            content = @Content(schema = @Schema(implementation = AgendaDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public Mono<ResponseEntity<AgendaDTO>> createAgenda(@RequestBody @Valid CreateAgendaRequest request) {
        log.info("Creating agenda with request: {}", request);
        return agendaService.createAgenda(request)
                .doOnNext(agenda -> log.info("Created agenda: {}", agenda))
                .map(session -> {
                    URI location = URI.create("/agendas/" + session.getId());
                    return ResponseEntity.created(location).body(session);
                });
    }

    @GetMapping
    @Operation(summary = "Get all agendas",
            description = "Get all agendas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Agendas retrieved",
                            content = @Content(schema = @Schema(implementation = AgendaDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Agenda Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public Flux<AgendaDTO> getAllAgendas() {
        log.info("Getting all agendas");
        return agendaService.getAllAgendas()
                .doOnNext(agenda -> log.info("Retrieved agenda: {}", agenda));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an agenda by id",
            description = "Get an agenda by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Agenda retrieved",
                            content = @Content(schema = @Schema(implementation = AgendaDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Agenda Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public Mono<ResponseEntity<AgendaDTO>> getAgendaById(@PathVariable String id) {
        log.info("Getting agenda with id: {}", id);
        return agendaService.getAgendaById(id)
                .map(agenda -> {
                    log.info("Retrieved agenda: {}", agenda);
                    return ResponseEntity.ok(agenda);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
