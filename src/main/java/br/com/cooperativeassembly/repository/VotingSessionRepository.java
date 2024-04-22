package br.com.cooperativeassembly.repository;


import br.com.cooperativeassembly.domain.entity.VotingSession;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;


@Repository
public interface VotingSessionRepository extends ReactiveMongoRepository<VotingSession, String> {

    @Query("{ 'startTime' : { $lt: ?0 }, 'status' : 'OPEN' }")
    Flux<VotingSession> findSessionsToClose(LocalDateTime now);

}
