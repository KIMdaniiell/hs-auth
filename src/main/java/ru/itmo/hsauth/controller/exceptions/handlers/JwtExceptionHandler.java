package ru.itmo.hsauth.controller.exceptions.handlers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itmo.hsauth.model.dto.ErrorDTO;

import java.time.LocalDateTime;

@ControllerAdvice
public class JwtExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected ErrorDTO handleExpiredJwtException(ExpiredJwtException e) {
        return new ErrorDTO(
                LocalDateTime.now(),
                e.getMessage(),
                "JWT token is expired"
        );
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected ErrorDTO handleSignatureException(SignatureException e) {
        return new ErrorDTO(
                LocalDateTime.now(),
                e.getMessage(),
                "Invalid Signature"
        );
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected ErrorDTO handleMalformedJwtException(MalformedJwtException e) {
        return new ErrorDTO(
                LocalDateTime.now(),
                e.getMessage(),
                "Not valid token"
        );
    }

}
