package br.com.cooperativeassembly.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private String timestamp;
    private int status;
    private String error;
    private String trace;
    private String message;
    private String path;
}
