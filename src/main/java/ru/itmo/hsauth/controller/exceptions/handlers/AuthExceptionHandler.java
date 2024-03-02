package ru.itmo.hsauth.controller.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itmo.hsauth.model.dto.ErrorDTO;

import java.time.LocalDateTime;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected ErrorDTO handleAuthenticationException(AuthenticationException e) {
        return new ErrorDTO(
                LocalDateTime.now(),
                e.getMessage(),
                "Authentication failed"
        );
    }

}
