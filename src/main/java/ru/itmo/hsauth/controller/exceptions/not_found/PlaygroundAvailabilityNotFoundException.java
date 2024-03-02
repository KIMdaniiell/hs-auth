package ru.itmo.hsauth.controller.exceptions.not_found;

public class PlaygroundAvailabilityNotFoundException extends NotFoundException{
    public PlaygroundAvailabilityNotFoundException(String filtersString) {
        super("playground availability", filtersString);
    }
}
