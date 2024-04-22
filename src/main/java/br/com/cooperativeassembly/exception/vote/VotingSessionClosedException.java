package br.com.cooperativeassembly.exception.vote;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class VotingSessionClosedException extends ResponseStatusException {

    public VotingSessionClosedException(String message) {
        super(BAD_REQUEST, message);
    }
    
}
