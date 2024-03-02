package ru.itmo.hsauth.controller.exceptions.already_applied;


public class PlayerAlreadyInTeamException extends AlreadyAppliedException {
    public PlayerAlreadyInTeamException(long playerId, long teamId) {
        super("Can't add player (id = " + playerId + ") to team (id = " + teamId + ") twice.");
    }
}
