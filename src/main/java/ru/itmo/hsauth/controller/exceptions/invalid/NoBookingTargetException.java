package ru.itmo.hsauth.controller.exceptions.invalid;

public class NoBookingTargetException extends InvalidRequestDataException {
    public NoBookingTargetException() {
        super("No booking target. Add not null player or team.");
    }
}
