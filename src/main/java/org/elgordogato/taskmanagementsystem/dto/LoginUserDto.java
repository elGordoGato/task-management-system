package org.elgordogato.taskmanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserDto {
    @Email
    private String email;

    @NotBlank
    @NotNull
    @Size(min = 6, max = 30)
    private String password;
}
