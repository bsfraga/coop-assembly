package br.com.cooperativeassembly.domain.request;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class CastVoteRequest {
    private String sessionId;
    private String memberId;
    private boolean decision;
}
