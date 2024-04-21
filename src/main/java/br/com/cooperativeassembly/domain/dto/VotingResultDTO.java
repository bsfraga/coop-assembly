package br.com.cooperativeassembly.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class VotingResultDTO {
    private String sessionId;
    private long yesVotes;
    private long noVotes;
}