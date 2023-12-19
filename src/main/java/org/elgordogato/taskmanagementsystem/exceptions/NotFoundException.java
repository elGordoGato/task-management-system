package org.elgordogato.taskmanagementsystem.exceptions;


public class NotFoundException extends RuntimeException {
    public NotFoundException(Class<?> type, Long id) {
        super(String.format("%s with id: %d was not found", type.getSimpleName(), id));
    }
}