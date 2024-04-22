package br.com.cooperativeassembly.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
@Builder
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String sessionId;
    private String memberId;
    private boolean decision;
}
