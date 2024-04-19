package br.com.cooperativeassembly.repository;

import br.com.cooperativeassembly.domain.entity.Agenda;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AgendaRepository extends ReactiveMongoRepository<Agenda, String> {

    Mono<Agenda> findById(String id);

}
