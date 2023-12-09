package org.elgordogato.taskmanagementsystem.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.elgordogato.taskmanagementsystem.utils.Marker;

import java.util.Date;

@Builder
@Getter
public class CommentDto {

    @NotNull(groups = Marker.OnUpdate.class)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 2000)
    private String text;

    private Long authorId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
}
