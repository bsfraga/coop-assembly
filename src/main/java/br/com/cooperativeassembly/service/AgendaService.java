package br.com.cooperativeassembly.service;

import br.com.cooperativeassembly.domain.dto.AgendaDTO;
import br.com.cooperativeassembly.domain.entity.Agenda;
import br.com.cooperativeassembly.domain.request.CreateAgendaRequest;
import br.com.cooperativeassembly.exception.agenda.AgendaCreationException;
import br.com.cooperativeassembly.exception.agenda.AgendaNotFoundException;
import br.com.cooperativeassembly.repository.AgendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public Mono<AgendaDTO> createAgenda(CreateAgendaRequest request) {
        Agenda agenda = Agenda.builder()
                .description(request.getDescription())
                .build();
        return agendaRepository.save(agenda)
                .map(this::convertToDTO)
                .onErrorResume(e -> Mono.error(new AgendaCreationException("Failed to save agenda", e)));
    }

    public Mono<AgendaDTO> getAgendaById(String id) {
        return agendaRepository.findById(id)
                .map(this::convertToDTO)
                .switchIfEmpty(Mono.error(new AgendaNotFoundException("Agenda not found")));
    }

    public Flux<AgendaDTO> getAllAgendas() {
        return agendaRepository.findAll()
                .map(this::convertToDTO)
                .onErrorResume(e -> Mono.error(new AgendaNotFoundException("Failed to retrieve agendas", e)));
    }

    private AgendaDTO convertToDTO(Agenda agenda) {
        return AgendaDTO.builder()
                .id(agenda.getId())
                .description(agenda.getDescription())
                .build();
    }
}