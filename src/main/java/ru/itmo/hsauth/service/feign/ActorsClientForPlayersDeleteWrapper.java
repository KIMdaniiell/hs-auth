package ru.itmo.hsauth.service.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.hsauth.controller.exceptions.fallback.ServiceUnavailableException;

@Component
@RequiredArgsConstructor
public class ActorsClientForPlayersDeleteWrapper {

    private final ActorsClient actorsClient;

    @CircuitBreaker(name = "PlayersDeleteCircuitBreaker", fallbackMethod = "getFallback")
    public void deletePlayerById(long playerId) {
        actorsClient.deletePlayerById(playerId);
    }

    public void getFallback(Throwable exception) throws ServiceUnavailableException {
        throw new ServiceUnavailableException("Actors service is temporarily unavailable"
                + "\n" + exception.getMessage());
    }
}
