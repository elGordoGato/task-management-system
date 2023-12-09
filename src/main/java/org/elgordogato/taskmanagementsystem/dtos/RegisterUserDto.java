package org.elgordogato.taskmanagementsystem.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserDto {
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @Size(max = 100)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 100)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    private String fullName;
}
