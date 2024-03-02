package ru.itmo.hsauth.service.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.hsauth.controller.exceptions.fallback.ServiceUnavailableException;

@Component
@RequiredArgsConstructor
public class ActorsClientForManagersByUserWrapper {

    private final ActorsClient actorsClient;

    @CircuitBreaker(name = "ManagerByUserCircuitBreaker", fallbackMethod = "getFallback")
    public long findTeamManagerIdByUserId(long userId) {
        return actorsClient.findTeamManagerIdByUserId(userId);
    }

    public long getFallback(Throwable exception) throws ServiceUnavailableException {
        throw new ServiceUnavailableException("Actors service is temporarily unavailable"
                + "\n" + exception.getMessage());
    }
}
