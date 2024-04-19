package br.com.cooperativeassembly.domain.enums;

public enum FieldType {

    TEXTO("Texto"),
    INPUT_TEXTO("Campo de texto"),
    INPUT_NUMERICO("Campo numérico"),
    INPUT_DATA("Campo data");

    private final String description;

    FieldType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
