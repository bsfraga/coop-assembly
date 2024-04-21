package br.com.cooperativeassembly.repository;


import br.com.cooperativeassembly.domain.entity.VotingSession;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface VotingSessionRepository extends ReactiveMongoRepository<VotingSession, String> {

    @Query("{ 'status': 'OPEN', 'startTime': { $lt: ?0 } }")
    Flux<VotingSession> findSessionsToClose(Long currentTime);

}
