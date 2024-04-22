package br.com.cooperativeassembly.domain.enums;

public enum ButtonAction {

    SUBMIT("Submit"),
    CANCEL("Cancelar");

    private final String description;

    ButtonAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
