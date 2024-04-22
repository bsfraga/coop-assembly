package br.com.cooperativeassembly.domain.dto.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class FormDTO extends ScreenDTO {
    @JsonProperty("itens")
    private List<FormItem> itens;
    @JsonProperty("botaoOk")
    private FormButton botaoOk;
    @JsonProperty("botaoCancelar")
    private FormButton botaoCancelar;
}