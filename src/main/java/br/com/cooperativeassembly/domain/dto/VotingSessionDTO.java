package br.com.cooperativeassembly.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class VotingSessionDTO {
    private String id;
    private String agendaId;
    private long startTime;
    private long duration;
}