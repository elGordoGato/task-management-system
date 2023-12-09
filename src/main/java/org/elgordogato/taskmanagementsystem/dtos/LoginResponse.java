package org.elgordogato.taskmanagementsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class LoginResponse {
    private final String token;

    private final long expiresIn;
}