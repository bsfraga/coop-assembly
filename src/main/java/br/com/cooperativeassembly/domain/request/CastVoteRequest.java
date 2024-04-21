package br.com.cooperativeassembly.domain.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class CastVoteRequest {

    @NotNull(message = "Member ID (CPF) cannot be null")
    @Pattern(regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})|(\\d{11})$", message = "Invalid CPF format")
    private String memberId;

    @NotNull(message = "Decision cannot be null")
    private Boolean decision;
}
