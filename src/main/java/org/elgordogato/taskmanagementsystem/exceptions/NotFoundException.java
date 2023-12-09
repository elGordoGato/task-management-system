package org.elgordogato.taskmanagementsystem.exceptions;


public class NotFoundException extends RuntimeException {
    private final Long idNotFound;

    public NotFoundException(Long id) {
        idNotFound = id;
    }

    @Override
    public String getMessage() {
        return String.valueOf(idNotFound);
    }
}
