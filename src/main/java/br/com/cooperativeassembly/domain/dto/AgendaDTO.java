package br.com.cooperativeassembly.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class AgendaDTO {
    private String id;
    private String description;
}
