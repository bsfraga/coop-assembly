package br.com.cooperativeassembly.service;

import br.com.cooperativeassembly.domain.dto.AgendaDTO;
import br.com.cooperativeassembly.domain.entity.Agenda;
import br.com.cooperativeassembly.domain.request.CreateAgendaRequest;
import br.com.cooperativeassembly.repository.AgendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
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
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to save agenda", e)));
    }

    public Flux<AgendaDTO> getAllAgendas() {
        return agendaRepository.findAll()
                .map(this::convertToDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve agendas", e)));
    }

    private AgendaDTO convertToDTO(Agenda agenda) {
        return AgendaDTO.builder()
                .id(agenda.getId())
                .description(agenda.getDescription())
                .build();
    }
}