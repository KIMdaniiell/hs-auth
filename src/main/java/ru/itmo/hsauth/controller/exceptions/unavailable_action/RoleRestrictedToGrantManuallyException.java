package ru.itmo.hsauth.controller.exceptions.unavailable_action;


import ru.itmo.hsauth.model.entity.Role;

public class RoleRestrictedToGrantManuallyException extends UnavailableActionException {
    public RoleRestrictedToGrantManuallyException(Role role) {
        super("User need to create entity for getting role:" + role.name());
    }
}
