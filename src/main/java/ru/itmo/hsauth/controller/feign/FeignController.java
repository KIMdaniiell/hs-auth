package ru.itmo.hsauth.controller.feign;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.hsauth.model.dto.RoleDTO;
import ru.itmo.hsauth.service.UserService;

import static ru.itmo.hsauth.controller.util.ValidationMessages.MSG_ID_NEGATIVE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/feign/users")
public class FeignController {

    private final UserService userService;


    @PutMapping("/{userId}/roles")
    public void addRole(
            @PathVariable @Min(value = 0, message = MSG_ID_NEGATIVE) long userId,
            @Validated @RequestBody RoleDTO role) {

        userService.addRole(userId, role, true);
    }

    @DeleteMapping("/{userId}/roles")
    public void removeRole(
            @PathVariable @Min(value = 0, message = MSG_ID_NEGATIVE) long userId,
            @Validated @RequestBody RoleDTO role) {

        userService.removeRole(userId, role, false);
    }

}
