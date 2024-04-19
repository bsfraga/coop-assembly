package br.com.cooperativeassembly.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Vote {
    @Id
    private String id;
    private String sessionId;
    private String memberId;
    private boolean decision;
}
