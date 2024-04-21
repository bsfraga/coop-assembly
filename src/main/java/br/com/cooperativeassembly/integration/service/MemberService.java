package br.com.cooperativeassembly.integration.service;

import br.com.cooperativeassembly.integration.dto.CpfValidationDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MemberService {

    private final WebClient webClient;

    public MemberService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://user-info.herokuapp.com").build();
    }

    public Mono<Boolean> isCpfValid(String memberId) {
        return webClient.get()
                .uri("/users/{cpf}", memberId)
                .retrieve()
                .bodyToMono(CpfValidationDTO.class)
                .map(response -> "ABLE_TO_VOTE".equals(response.getStatus()))
                .onErrorReturn(false);
    }
}