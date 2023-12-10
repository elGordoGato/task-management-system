package org.elgordogato.taskmanagementsystem.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.elgordogato.taskmanagementsystem.utils.Marker;

@Data
@Builder
public class UserDto {

    private Long id;

    @NotNull
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @Size(max = 100)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 100)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @NotNull(groups = Marker.OnCreate.class)
    @NotBlank(groups = Marker.OnCreate.class)
    @Size(min = 2, max = 100)
    private String fullName;
}
