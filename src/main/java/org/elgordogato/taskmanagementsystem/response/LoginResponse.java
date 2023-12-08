package org.elgordogato.taskmanagementsystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class LoginResponse {
    private final String token;

    private final long expiresIn;
}
