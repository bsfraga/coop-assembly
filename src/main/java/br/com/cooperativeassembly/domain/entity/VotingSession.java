package br.com.cooperativeassembly.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
@Builder
@With
@EntityScan
public class VotingSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String agendaId;
    private long startTime;
    private long duration;
}
