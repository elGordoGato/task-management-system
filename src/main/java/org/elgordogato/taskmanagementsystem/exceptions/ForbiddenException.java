package org.elgordogato.taskmanagementsystem.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(Long userId, String message, Long targetId) {
        super(String.format("User with id: %d has no rights to %s with id: %d", userId, message, targetId));
    }
}
