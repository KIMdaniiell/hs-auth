package ru.itmo.hsauth.controller.exceptions.unavailable_action;


import ru.itmo.hsauth.controller.exceptions.ControllerException;

public class UnavailableActionException extends ControllerException {
    public UnavailableActionException(String message) {
        super("Action is unavailable", message);
    }
}
