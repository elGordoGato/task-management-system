package org.elgordogato.taskmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserDto {
    @Email
    private String email;

    @NotBlank
    @NotNull
    @Size(min = 6, max = 30)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @NotBlank
    @NotNull
    @Size(min = 2, max = 30)
    private String name;
}
