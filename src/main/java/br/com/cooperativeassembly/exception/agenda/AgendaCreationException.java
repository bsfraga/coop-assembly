package br.com.cooperativeassembly.exception.agenda;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class AgendaCreationException extends ResponseStatusException {

    public AgendaCreationException(String message) {
        super(BAD_REQUEST, message);
    }

    public AgendaCreationException(String message, Throwable cause) {
        super(BAD_REQUEST, message, cause);
    }

}
