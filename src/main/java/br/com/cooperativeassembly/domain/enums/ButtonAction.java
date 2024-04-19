package br.com.cooperativeassembly.domain.enums;

public enum ButtonAction {

    SUBMIT("Submit"),
    CANCEL("Cancel");

    private final String action;

    ButtonAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

}
