package br.com.cooperativeassembly.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAgendaRequest {
    @NotNull
    @NotBlank(message = "Description is mandatory")
    private String description;
    private String details;
}