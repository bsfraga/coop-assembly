package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.dto.ui.FormButton;
import br.com.cooperativeassembly.domain.dto.ui.FormDTO;
import br.com.cooperativeassembly.domain.dto.ui.InputFormItem;
import br.com.cooperativeassembly.domain.dto.ui.TextFormItem;
import br.com.cooperativeassembly.service.metadata.FormUIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static br.com.cooperativeassembly.domain.enums.FieldType.*;
import static br.com.cooperativeassembly.domain.enums.FieldType.INPUT_DATA;
import static br.com.cooperativeassembly.domain.enums.ScreenType.FORMULARIO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@WebFluxTest(FormController.class)
class FormControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FormUIService formUIService;

    @Test
    void getForm() {
        TextFormItem textItem = TextFormItem.builder()
                .tipo(TEXTO.name())
                .texto("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                .build();

        InputFormItem inputTextItem = InputFormItem.builder()
                .tipo(INPUT_TEXTO.name())
                .id("idCampoTexto")
                .titulo(INPUT_TEXTO.getDescription())
                .valor("Texto")
                .build();

        InputFormItem inputNumberItem = InputFormItem.builder()
                .tipo(INPUT_NUMERICO.name())
                .id("idCampoNumerico")
                .titulo(INPUT_NUMERICO.getDescription())
                .valor("999")
                .build();

        InputFormItem inputDateItem = InputFormItem.builder()
                .tipo(INPUT_DATA.name())
                .id("idCampoData")
                .titulo(INPUT_DATA.getDescription())
                .valor("01/01/2000")
                .build();

        FormButton actionButton = FormButton.builder()
                .texto("Ação 1")
                .url("http://seudominio.com/ACAO1")
                .body(Map.of(
                        "campo1", "valor1",
                        "campo2", 123
                ))
                .build();

        FormButton cancelButton = FormButton.builder()
                .texto("Cancelar")
                .url("http://seudominio.com/")
                .build();

        FormDTO formDTO = FormDTO.builder()
                .tipo(FORMULARIO.name())
                .titulo("TITULO TELA")
                .itens(List.of(textItem, inputTextItem, inputNumberItem, inputDateItem))
                .botaoOk(actionButton)
                .botaoCancelar(cancelButton)
                .build();

        given(formUIService.getForm()).willReturn(Mono.just(formDTO));

        webTestClient.get().uri("/form")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FormDTO.class)
                .consumeWith(response -> {
                    assertEquals(formDTO.getTipo(), response.getResponseBody().getTipo());
                    assertEquals(formDTO.getTitulo(), response.getResponseBody().getTitulo());
                    assertEquals(formDTO.getItens(), response.getResponseBody().getItens());
                    assertEquals(formDTO.getBotaoOk(), response.getResponseBody().getBotaoOk());
                    assertEquals(formDTO.getBotaoCancelar(), response.getResponseBody().getBotaoCancelar());
                });
    }
}
