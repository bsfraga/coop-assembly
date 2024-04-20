package br.com.cooperativeassembly.service.metadata;

import br.com.cooperativeassembly.domain.dto.ui.FormButton;
import br.com.cooperativeassembly.domain.dto.ui.SelectionDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class SelectionUIService {


    public Mono<SelectionDTO> getSelection() {
        FormButton item1 = FormButton.builder()
                .texto("Opção 1")
                .url("http://seudominio.com/OPT1")
                .body(Map.of("dadosOpcao", "campo de teste"))
                .build();

        FormButton item2 = FormButton.builder()
                .texto("Opção 2")
                .url("http://seudominio.com/OPT2")
                .build();

        FormButton item3 = FormButton.builder()
                .texto("Opção 3")
                .url("http://seudominio.com/OPT3")
                .build();

        FormButton item4 = FormButton.builder()
                .texto("Opção 4")
                .url("http://seudominio.com/OPT4")
                .build();

        List<FormButton> itens = List.of(item1, item2, item3, item4);

        SelectionDTO selection = SelectionDTO.builder()
                .tipo("SELECAO")
                .titulo("Lista de seleção")
                .itens(itens)
                .build();

        return Mono.just(selection);
    }
}
