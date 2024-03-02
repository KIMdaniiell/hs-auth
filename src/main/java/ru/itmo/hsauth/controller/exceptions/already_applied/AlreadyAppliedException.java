package ru.itmo.hsauth.controller.exceptions.already_applied;


import ru.itmo.hsauth.controller.exceptions.ControllerException;

public class AlreadyAppliedException  extends ControllerException {
    public AlreadyAppliedException(String message) {
        super("Already applied action", message);
    }
}
