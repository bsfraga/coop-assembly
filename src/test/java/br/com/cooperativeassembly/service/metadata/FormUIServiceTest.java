package br.com.cooperativeassembly.service.metadata;

import br.com.cooperativeassembly.domain.dto.ui.FormButton;
import br.com.cooperativeassembly.domain.dto.ui.FormDTO;
import br.com.cooperativeassembly.domain.dto.ui.InputFormItem;
import br.com.cooperativeassembly.domain.dto.ui.TextFormItem;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

import static br.com.cooperativeassembly.domain.enums.FieldType.*;
import static br.com.cooperativeassembly.domain.enums.ScreenType.FORMULARIO;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FormUIServiceTest {

    @InjectMocks
    private FormUIService formUIService = new FormUIService();

    @Test
    void getFormTest() {

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

        StepVerifier.create(formUIService.getForm())
                .assertNext(form -> {
                    assertEquals(formDTO.getTipo(), form.getTipo());
                    assertEquals(formDTO.getTitulo(), form.getTitulo());
                    assertEquals(formDTO.getItens(), form.getItens());
                    assertEquals(formDTO.getBotaoOk(), form.getBotaoOk());
                    assertEquals(formDTO.getBotaoCancelar(), form.getBotaoCancelar());
                })
                .verifyComplete();

    }
}
