package br.com.cooperativeassembly.integration.service;

import br.com.cooperativeassembly.integration.dto.CpfValidationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static java.lang.Boolean.TRUE;

@Service
@Slf4j
public class MemberService {

    private final WebClient webClient;

    public MemberService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.4devs.com.br/ferramentas_online.php").build();
    }

    public Mono<Boolean> isCpfValid(String memberId) {
        log.info("Validating CPF for member: {}", memberId);
        return webClient.post()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .bodyValue("acao=validar_cpf&txt_cpf=" + memberId)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> log.info("Received response for CPF validation: {}", response))
                .map(response -> response.contains("Verdadeiro"))
                .doOnSuccess(isValid -> log.info("CPF validation result for member {}: {}", memberId, TRUE.equals(isValid) ? "valid" : "invalid"))
                .onErrorReturn(false);
    }
}
