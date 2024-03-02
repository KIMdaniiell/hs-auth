package ru.itmo.hsauth.controller;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.hsauth.model.dto.JwtTokenDTO;
import ru.itmo.hsauth.model.dto.MessageDTO;
import ru.itmo.hsauth.model.dto.UserDTO;
import ru.itmo.hsauth.service.AuthService;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public MessageDTO register(@Validated @RequestBody UserDTO userDTO) {
        return new MessageDTO(
                "User added to the system",
                authService.saveUser(userDTO).toString(),
                null);
    }

    @PostMapping("/token")
    public MessageDTO token(@Validated @RequestBody UserDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getLogin(),
                        userDTO.getPassword()));

        return new MessageDTO(
                "User entered the system",
                null,
                authService.generateToken(userDTO.getLogin())
        );
    }

    @PostMapping("/validateToken")
    public MessageDTO validateToken(@Validated @RequestBody JwtTokenDTO token) {
        Claims claims = authService.validate(token.getToken());
        return new MessageDTO(
                "Token is valid",
                claims.toString(),
                null);
    }
}
