package br.com.cooperativeassembly.service.metadata;

import br.com.cooperativeassembly.domain.dto.ui.FormButton;
import br.com.cooperativeassembly.domain.dto.ui.FormDTO;
import br.com.cooperativeassembly.domain.dto.ui.InputFormItem;
import br.com.cooperativeassembly.domain.dto.ui.TextFormItem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static br.com.cooperativeassembly.domain.enums.FieldType.*;
import static br.com.cooperativeassembly.domain.enums.ScreenType.FORMULARIO;

@Service
@Slf4j
@AllArgsConstructor
public class FormUIService {

    public Mono<FormDTO> getForm() {
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

        // Construir botões do formulário
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

        return Mono.just(formDTO);
    }

}
