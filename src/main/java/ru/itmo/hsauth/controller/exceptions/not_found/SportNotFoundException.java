package ru.itmo.hsauth.controller.exceptions.not_found;

public class SportNotFoundException extends NotFoundException {

    public SportNotFoundException(String filtersString) {
        super("sport", filtersString);
    }
}
