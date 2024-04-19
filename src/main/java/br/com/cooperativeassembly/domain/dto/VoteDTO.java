package br.com.cooperativeassembly.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class VoteDTO {
    private String id;
    private String sessionId;
    private String memberId;
    private boolean decision;
}