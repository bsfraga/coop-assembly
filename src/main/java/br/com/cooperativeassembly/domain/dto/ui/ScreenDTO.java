package br.com.cooperativeassembly.domain.dto.ui;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ScreenDTO {

    @JsonProperty("tipo")
    protected String tipo;
    @JsonProperty("titulo")
    protected String titulo;

}
