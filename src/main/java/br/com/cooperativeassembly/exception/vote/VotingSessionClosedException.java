package br.com.cooperativeassembly.exception.vote;

public class VotingSessionClosedException extends RuntimeException {

    public VotingSessionClosedException(String message) {
        super(message);
    }
    
}
