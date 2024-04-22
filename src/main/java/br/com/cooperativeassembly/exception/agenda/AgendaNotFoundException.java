package br.com.cooperativeassembly.exception.agenda;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class AgendaNotFoundException extends ResponseStatusException {

    public AgendaNotFoundException(String message) {
        super(NOT_FOUND, message);
    }

    public AgendaNotFoundException(String message, Throwable cause) {
        super(NOT_FOUND, message, cause);
    }
}
