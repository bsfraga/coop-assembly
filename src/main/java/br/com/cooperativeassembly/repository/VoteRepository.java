package br.com.cooperativeassembly.repository;

import br.com.cooperativeassembly.domain.entity.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;


@Repository
public interface VoteRepository extends ReactiveMongoRepository<Vote, String> {

    Flux<Vote> findBySessionId(String sessionId);
}
