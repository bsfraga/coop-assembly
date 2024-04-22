package br.com.cooperativeassembly.exception.votingsession;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class VotingSessionNotFoundException extends ResponseStatusException {

    public VotingSessionNotFoundException(String reason) {
        super(NOT_FOUND, reason);
    }
}
