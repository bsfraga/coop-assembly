package br.com.cooperativeassembly.domain.entity;

import br.com.cooperativeassembly.domain.enums.VotingStatus;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    private LocalDateTime startTime;
    @Builder.Default
    private long duration = 1;
    @Builder.Default
    private VotingStatus status = VotingStatus.OPEN;
}
