package ru.itmo.hsauth.controller.exceptions.fallback;


import ru.itmo.hsauth.controller.exceptions.ControllerException;

public class ServiceUnavailableException extends ControllerException {

    public ServiceUnavailableException(String message) {
        super("Service unavailable", message);
    }
}
