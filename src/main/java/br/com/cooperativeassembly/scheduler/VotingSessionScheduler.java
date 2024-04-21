package br.com.cooperativeassembly.scheduler;

import br.com.cooperativeassembly.repository.VotingSessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

import static br.com.cooperativeassembly.domain.enums.VotingStatus.CLOSED;

@Slf4j
@Component
@AllArgsConstructor
public class VotingSessionScheduler {

    private final VotingSessionRepository votingSessionRepository;

    @Scheduled(fixedRate = 60000)
    public void closeVotingSessions() {
        votingSessionRepository.findSessionsToClose(System.currentTimeMillis())
                .flatMap(session -> {
                    session.setStatus(CLOSED);
                    return votingSessionRepository.save(session);
                })
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(
                        session -> log.info("Voting session {} closed", session.getId()),
                        error -> log.error("Error closing voting session", error)
                );
    }

}
