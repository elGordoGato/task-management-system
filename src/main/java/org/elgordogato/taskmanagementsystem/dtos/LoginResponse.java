package org.elgordogato.taskmanagementsystem.dtos;

public record LoginResponse(String token, long expiresIn) {
}