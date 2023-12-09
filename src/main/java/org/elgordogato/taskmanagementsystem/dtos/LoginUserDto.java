package org.elgordogato.taskmanagementsystem.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoginUserDto {

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @Size(max = 100)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
}
