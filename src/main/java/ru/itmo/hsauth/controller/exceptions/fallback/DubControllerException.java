package ru.itmo.hsauth.controller.exceptions.fallback;


import ru.itmo.hsauth.controller.exceptions.ControllerException;

public class DubControllerException extends ControllerException {

    private final int statusCode;

    public DubControllerException(int statusCode, String error, String message) {
        super(error, message);
        this.statusCode = statusCode;
    }
}
