package br.com.cooperativeassembly.domain.dto.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.util.List;

@Data
@Builder
@With
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormDTO {
    private String tipo;
    private String titulo;
    private List<FormItem> itens;
    private FormButton botaoOk;
    private FormButton botaoCancelar;
}
