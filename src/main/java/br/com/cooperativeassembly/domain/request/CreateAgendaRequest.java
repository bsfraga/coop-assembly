package br.com.cooperativeassembly.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAgendaRequest {
    private String description;
    private String details;
}