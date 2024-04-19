package br.com.cooperativeassembly.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateVotingSessionRequest {
    private String agendaId;
    private long duration;
}