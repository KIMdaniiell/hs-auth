package ru.itmo.hsauth.controller.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ControllerException extends RuntimeException {

    private final LocalDateTime timestamp;
    private final String error;
    private final String message;

    public ControllerException(String error, String message) {
        timestamp = LocalDateTime.now();
        this.message = message;
        this.error = error;
    }
}
