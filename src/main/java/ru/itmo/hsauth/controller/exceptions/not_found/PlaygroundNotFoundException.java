package ru.itmo.hsauth.controller.exceptions.not_found;

public class PlaygroundNotFoundException extends NotFoundException {
    public PlaygroundNotFoundException(String filtersString) {
        super("playground", filtersString);
    }
}
