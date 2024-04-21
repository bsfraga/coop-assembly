package br.com.cooperativeassembly.service;

import br.com.cooperativeassembly.domain.entity.Agenda;
import br.com.cooperativeassembly.domain.request.CreateAgendaRequest;
import br.com.cooperativeassembly.exception.agenda.AgendaCreationException;
import br.com.cooperativeassembly.exception.agenda.AgendaNotFoundException;
import br.com.cooperativeassembly.repository.AgendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AgendaServiceTest {

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private AgendaService agendaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAgenda_Success() {
        CreateAgendaRequest request = CreateAgendaRequest.builder()
                .description("Test Agenda")
                .build();

        Agenda agenda = Agenda.builder()
                .id("1")
                .description("Test Agenda")
                .build();

        when(agendaRepository.save(any(Agenda.class))).thenReturn(Mono.just(agenda));

        StepVerifier.create(agendaService.createAgenda(request))
                .expectNextMatches(agendaDTO -> agendaDTO.getDescription().equals("Test Agenda"))
                .verifyComplete();
    }

    @Test
    void createAgenda_Failure() {
        CreateAgendaRequest request = CreateAgendaRequest.builder()
                .description("Test Agenda")
                .build();

        when(agendaRepository.save(any(Agenda.class))).thenReturn(Mono.error(new AgendaCreationException("Failed to save agenda")));

        StepVerifier.create(agendaService.createAgenda(request))
                .expectError(AgendaCreationException.class)
                .verify();
    }

    @Test
    void getAllAgendas_Success() {
        Agenda agenda1 = Agenda.builder()
                .id("1")
                .description("Test Agenda 1")
                .build();

        Agenda agenda2 = Agenda.builder()
                .id("2")
                .description("Test Agenda 2")
                .build();

        when(agendaRepository.findAll()).thenReturn(Flux.just(agenda1, agenda2));

        StepVerifier.create(agendaService.getAllAgendas())
                .expectNextMatches(agendaDTO -> agendaDTO.getDescription().equals("Test Agenda 1"))
                .expectNextMatches(agendaDTO -> agendaDTO.getDescription().equals("Test Agenda 2"))
                .verifyComplete();
    }

    @Test
    void getAllAgendas_Failure() {
        when(agendaRepository.findAll()).thenReturn(Flux.error(new AgendaNotFoundException("Failed to retrieve agendas")));

        StepVerifier.create(agendaService.getAllAgendas())
                .expectError(AgendaNotFoundException.class)
                .verify();
    }
}