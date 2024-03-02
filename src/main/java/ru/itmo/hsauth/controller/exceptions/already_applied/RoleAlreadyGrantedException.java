package ru.itmo.hsauth.controller.exceptions.already_applied;


import ru.itmo.hsauth.model.entity.Role;

public class RoleAlreadyGrantedException extends AlreadyAppliedException {
    public RoleAlreadyGrantedException(long id, Role role) {
        super("User (id = " + id + ") already is " + role.name());
    }
}
