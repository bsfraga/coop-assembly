package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.dto.ui.FormDTO;
import br.com.cooperativeassembly.service.metadata.FormUIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/form")
@Tag(name = "Form")
@AllArgsConstructor
public class FormController {

    private final FormUIService formUIService;

    @GetMapping
    @Operation(summary = "Get form UI metadata")
    public Mono<FormDTO> getForm() {
        return formUIService.getForm();
    }

}
