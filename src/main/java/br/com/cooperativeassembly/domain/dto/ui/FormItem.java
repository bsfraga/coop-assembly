package br.com.cooperativeassembly.domain.dto.ui;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tipo", include = JsonTypeInfo.As.EXISTING_PROPERTY, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InputFormItem.class, name = "botao"),
        @JsonSubTypes.Type(value = TextFormItem.class, name = "campo"),
        @JsonSubTypes.Type(value = FormButton.class, name = "botaoOk"),
        @JsonSubTypes.Type(value = FormButton.class, name = "botaoCancelar"),
        @JsonSubTypes.Type(value = TextFormItem.class, name = "TEXTO"),
        @JsonSubTypes.Type(value = InputFormItem.class, name = "INPUT_TEXTO"),
        @JsonSubTypes.Type(value = InputFormItem.class, name = "INPUT_NUMERICO"),
        @JsonSubTypes.Type(value = InputFormItem.class, name = "INPUT_DATA")
})
public interface FormItem {
    String getTipo();
}
