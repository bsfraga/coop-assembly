package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.ErrorResponse;
import br.com.cooperativeassembly.domain.dto.ui.SelectionDTO;
import br.com.cooperativeassembly.service.metadata.SelectionUIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/selection")
@Tag(name = "Selection")
@AllArgsConstructor
@Slf4j
public class SelectionController {

    private final SelectionUIService selectionService;

    @GetMapping
    @Operation(summary = "Get selection UI metadata",
            description = "Get the metadata of the selection UI",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Selection metadata retrieved",
                            content = @Content(schema = @Schema(implementation = SelectionDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Selection Metadata Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public Mono<SelectionDTO> getSelection() {
        log.info("Iniciando a obtenção dos metadados da UI de seleção");
        Mono<SelectionDTO> selectionDTOMono = selectionService.getSelection();
        log.info("Metadados da UI de seleção obtidos com sucesso");
        return selectionDTOMono;
    }

}
