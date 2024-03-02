package ru.itmo.hsauth.controller.exceptions.not_found;

public class TeamNotFoundException extends NotFoundException{
    public TeamNotFoundException(String filtersString) {
        super("team", filtersString);
    }
}
