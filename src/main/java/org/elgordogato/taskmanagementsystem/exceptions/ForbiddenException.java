package org.elgordogato.taskmanagementsystem.exceptions;

public class ForbiddenException extends RuntimeException {
    private final Long idNotPermitted;

    public ForbiddenException(Long idNotPermitted) {
        this.idNotPermitted = idNotPermitted;
    }

    @Override
    public String getMessage() {
        return String.valueOf(idNotPermitted);
    }
}
