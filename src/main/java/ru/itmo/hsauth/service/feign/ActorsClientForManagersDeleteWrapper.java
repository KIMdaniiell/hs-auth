package ru.itmo.hsauth.service.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.hsauth.controller.exceptions.fallback.ServiceUnavailableException;

@Component
@RequiredArgsConstructor
public class ActorsClientForManagersDeleteWrapper {

    private final ActorsClient actorsClient;

    @CircuitBreaker(name = "ManagersDeleteCircuitBreaker", fallbackMethod = "getFallback")
    public void deleteManagerById(long managerId) {
        actorsClient.deleteTeamManagerById(managerId);
    }

    public void getFallback(Throwable exception) throws ServiceUnavailableException {
        throw new ServiceUnavailableException("Actors service is temporarily unavailable"
                + "\n" + exception.getMessage());
    }
}
