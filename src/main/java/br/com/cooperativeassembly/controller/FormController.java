package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.ErrorResponse;
import br.com.cooperativeassembly.domain.dto.ui.FormDTO;
import br.com.cooperativeassembly.service.metadata.FormUIService;
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
@RequestMapping("/form")
@Tag(name = "Form")
@AllArgsConstructor
@Slf4j
public class FormController {

    private final FormUIService formUIService;

    @GetMapping
    @Operation(summary = "Get form UI metadata",
        description = "Get the metadata of the form UI",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Form metadata retrieved",
                            content = @Content(schema = @Schema(implementation = FormDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Form Metadata Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public Mono<FormDTO> getForm() {
        log.info("Iniciando a obtenção dos metadados da UI do formulário");
        Mono<FormDTO> formDTOMono = formUIService.getForm();
        log.info("Metadados da UI do formulário obtidos com sucesso");
        return formDTOMono;
    }

}
