package br.com.cooperativeassembly.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.time.LocalDateTime;

@Data
@Builder
@With
public class VotingSessionDTO {
    private String id;
    private String agendaId;
    private LocalDateTime startTime;
    private long duration;
}