package ru.itmo.hsauth.controller.exceptions.invalid;

public class InvalidAvailabilityException extends InvalidRequestDataException {
    public InvalidAvailabilityException(String reason) {
        super("Can't set such pg availability, reason: " + reason);
    }
}
