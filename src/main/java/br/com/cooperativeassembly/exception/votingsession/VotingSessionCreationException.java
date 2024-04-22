package br.com.cooperativeassembly.exception.votingsession;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class VotingSessionCreationException extends ResponseStatusException {

    public VotingSessionCreationException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public VotingSessionCreationException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }

    public VotingSessionCreationException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public VotingSessionCreationException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }


}
