package br.com.cooperativeassembly.domain.enums;

public enum ScreenType {

    FORMULARIO("FORMULARIO"),
    SELECAO("SELECAO");

    private final String description;

    ScreenType(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
