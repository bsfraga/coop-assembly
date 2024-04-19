package br.com.cooperativeassembly.domain.dto.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormButton {
    private String texto;
    private String url;
    private Map<String, Object> body;
}
