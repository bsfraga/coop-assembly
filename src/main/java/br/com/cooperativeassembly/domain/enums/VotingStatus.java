package br.com.cooperativeassembly.domain.enums;

public enum VotingStatus {

    OPEN("OPEN"),
    CLOSED("CLOSED");

    private final String description;

    VotingStatus(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
