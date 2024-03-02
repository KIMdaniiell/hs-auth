package ru.itmo.hsauth.controller.exceptions.unavailable_action;


public class PlaygroundNotAvailableException extends UnavailableActionException {
    public PlaygroundNotAvailableException(long playgroundId) {
        super("Playground (id = " + playgroundId + ") is not available (closed)");
    }
}
