package br.com.cooperativeassembly.service.metadata;

import br.com.cooperativeassembly.domain.dto.ui.FormButton;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

import static br.com.cooperativeassembly.domain.enums.ScreenType.SELECAO;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SelectionUIServiceTest {

    @InjectMocks
    private SelectionUIService selectionUIService = new SelectionUIService();

    @Test
    void getSelectionTest() {

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

        StepVerifier.create(selectionUIService.getSelection())
                .expectNextMatches(selectionDTO -> {
                    assertEquals(SELECAO.name(), selectionDTO.getTipo());
                    assertEquals("Lista de seleção", selectionDTO.getTitulo());
                    assertEquals(itens, selectionDTO.getItens());
                    return true;
                })
                .verifyComplete();
    }
}
