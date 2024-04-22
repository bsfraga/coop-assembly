package br.com.cooperativeassembly.controller;

import br.com.cooperativeassembly.domain.dto.AgendaDTO;
import br.com.cooperativeassembly.domain.request.CreateAgendaRequest;
import br.com.cooperativeassembly.service.AgendaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(AgendaController.class)
class AgendaControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AgendaService agendaService;

    @Test
    void testCreateAgenda() {
        CreateAgendaRequest request = CreateAgendaRequest.builder()
                .description("Test Agenda")
                .build();

        AgendaDTO agendaDTO = AgendaDTO.builder()
                .id("1")
                .description("Test Agenda")
                .build();

        when(agendaService.createAgenda(any(CreateAgendaRequest.class))).thenReturn(Mono.just(agendaDTO));

        webTestClient.post()
                .uri("/agendas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AgendaDTO.class);
    }

    @Test
    void testGetAllAgendas() {
        AgendaDTO agendaDTO1 = AgendaDTO.builder()
                .id("1")
                .description("Test Agenda 1")
                .build();

        AgendaDTO agendaDTO2 = AgendaDTO.builder()
                .id("2")
                .description("Test Agenda 2")
                .build();

        when(agendaService.getAllAgendas()).thenReturn(Flux.just(agendaDTO1, agendaDTO2));

        webTestClient.get()
                .uri("/agendas")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AgendaDTO.class);
    }

    @Test
    void testGetAgendaById() {
        String agendaId = "1";
        AgendaDTO agendaDTO = AgendaDTO.builder()
                .id(agendaId)
                .description("Test Agenda")
                .build();

        when(agendaService.getAgendaById(agendaId)).thenReturn(Mono.just(agendaDTO));

        webTestClient.get()
                .uri("/agendas/{id}", agendaId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AgendaDTO.class);
    }

    @Test
    void testCreateAgendaWithInvalidRequest() {
        CreateAgendaRequest request = CreateAgendaRequest.builder()
                .description(null)
                .build();

        webTestClient.post()
                .uri("/agendas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testGetAllAgendasWhenNoAgendas() {
        when(agendaService.getAllAgendas()).thenReturn(Flux.empty());

        webTestClient.get()
                .uri("/agendas")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AgendaDTO.class)
                .hasSize(0);
    }

    @Test
    void testGetAgendaByIdWithNonExistentId() {
        String agendaId = "nonExistentId";
        when(agendaService.getAgendaById(agendaId)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/agendas/{id}", agendaId)
                .exchange()
                .expectStatus().isNotFound();
    }
}