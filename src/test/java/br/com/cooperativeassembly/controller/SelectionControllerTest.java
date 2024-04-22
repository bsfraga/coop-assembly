package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.dto.ui.FormButton;
import br.com.cooperativeassembly.domain.dto.ui.SelectionDTO;
import br.com.cooperativeassembly.service.metadata.SelectionUIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static br.com.cooperativeassembly.domain.enums.ScreenType.SELECAO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebFluxTest(SelectionController.class)
class SelectionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private SelectionUIService selectionUIService;

    @Test
    void getSelection() {
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
                .tipo(SELECAO.name())
                .titulo("Lista de seleção")
                .itens(itens)
                .build();

        when(selectionUIService.getSelection()).thenReturn(Mono.just(selection));

        webTestClient.get()
                .uri("/selection")
                .exchange()
                .expectStatus().isOk()
                .expectBody(SelectionDTO.class)
                .consumeWith(response -> {
                    SelectionDTO selectionDTO = response.getResponseBody();
                    assert selectionDTO != null;
                    assertEquals(selectionDTO.getTipo(), selection.getTipo());
                    assertEquals(selectionDTO.getTitulo(), selection.getTitulo());
                    assertEquals(selectionDTO.getItens().size(), selection.getItens().size());
                });
    }
}
