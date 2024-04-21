package br.com.cooperativeassembly.exception.agenda;

import lombok.AllArgsConstructor;

public class AgendaCreationException extends RuntimeException {

    public AgendaCreationException(String message) {
        super(message);
    }

    public AgendaCreationException(String message, Throwable cause) {
        super(message, cause);
    }

}
