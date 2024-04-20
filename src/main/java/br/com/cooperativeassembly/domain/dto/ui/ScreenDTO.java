package br.com.cooperativeassembly.domain.dto.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class ScreenDTO {

    protected String tipo;
    protected String titulo;

}
