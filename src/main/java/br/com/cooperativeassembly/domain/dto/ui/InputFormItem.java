package br.com.cooperativeassembly.domain.dto.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputFormItem implements FormItem {

    private String tipo;
    private String id;
    private String titulo;
    private String valor;

}
