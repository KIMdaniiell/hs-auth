package ru.itmo.hsauth.controller.exceptions.not_found;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String filtersString) {
        super("user", filtersString);
    }
}
