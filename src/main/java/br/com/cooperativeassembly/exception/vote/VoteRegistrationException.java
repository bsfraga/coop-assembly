package br.com.cooperativeassembly.exception.vote;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class VoteRegistrationException extends ResponseStatusException {

    public VoteRegistrationException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
