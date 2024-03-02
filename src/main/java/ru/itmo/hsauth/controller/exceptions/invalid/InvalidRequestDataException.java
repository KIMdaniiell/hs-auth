package ru.itmo.hsauth.controller.exceptions.invalid;


import ru.itmo.hsauth.controller.exceptions.ControllerException;

public class InvalidRequestDataException extends ControllerException {
    public InvalidRequestDataException(String message) {
        super("Invalid request", message);
    }
}