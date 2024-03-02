package ru.itmo.hsauth.service.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.hsauth.controller.exceptions.fallback.ServiceUnavailableException;

@Component
@RequiredArgsConstructor
public class ActorsClientForPlayerByUserWrapper {

    private final ActorsClient actorsClient;

    @CircuitBreaker(name = "PlayerByUserCircuitBreaker", fallbackMethod = "getFallback")
    public long findPlayerIdByUserId(long userId) {
        return actorsClient.findPlayerIdByUserId(userId);
    }

    public long getFallback(Throwable exception) throws ServiceUnavailableException {
        throw new ServiceUnavailableException("Actors service is temporarily unavailable"
                + "\n" + exception.getMessage());
    }
}
