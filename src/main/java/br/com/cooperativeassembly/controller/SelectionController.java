package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.dto.ui.SelectionDTO;
import br.com.cooperativeassembly.service.metadata.SelectionUIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/selection")
@Tag(name = "Selection")
@AllArgsConstructor
public class SelectionController {

    private final SelectionUIService selectionService;

    @GetMapping
    @Operation(summary = "Get selection UI metadata")
    public Mono<SelectionDTO> getSelection() {
        return selectionService.getSelection();
    }

}
