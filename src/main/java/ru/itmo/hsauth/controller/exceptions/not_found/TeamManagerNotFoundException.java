package ru.itmo.hsauth.controller.exceptions.not_found;

public class TeamManagerNotFoundException extends NotFoundException{
    public TeamManagerNotFoundException(String filtersString) {
        super("team manager", filtersString);
    }
}
