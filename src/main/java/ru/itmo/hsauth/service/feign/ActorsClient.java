package ru.itmo.hsauth.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "actors-service")
public interface ActorsClient {

    @GetMapping("feign/players")
    long findPlayerIdByUserId(@RequestParam long userId);

    @DeleteMapping("feign/players/{playerId}")
    void deletePlayerById(@PathVariable long playerId);

    @GetMapping("feign/managers")
    long findTeamManagerIdByUserId(@RequestParam long userId);

    @DeleteMapping("feign/managers/{managerId}")
    void deleteTeamManagerById(@PathVariable long managerId);
}
