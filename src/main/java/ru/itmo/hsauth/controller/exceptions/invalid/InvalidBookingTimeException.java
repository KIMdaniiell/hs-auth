package ru.itmo.hsauth.controller.exceptions.invalid;

public class InvalidBookingTimeException extends InvalidRequestDataException {
    public InvalidBookingTimeException(String message) {
        super("Booking can't be added: " + message);
    }
}
