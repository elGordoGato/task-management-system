package org.elgordogato.taskmanagementsystem.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.elgordogato.taskmanagementsystem.enums.TaskPriorityEnum;
import org.elgordogato.taskmanagementsystem.enums.TaskStatusEnum;
import org.elgordogato.taskmanagementsystem.utils.Marker;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class TaskDto {
    @NotNull(groups = Marker.OnUpdate.class)
    private Long id;

    @NotNull(groups = Marker.OnCreate.class)
    @NotBlank(groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    @Size(max = 200,
            groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private String title;

    @Size(max = 2000,
            groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private String description;

    private TaskStatusEnum status;

    private TaskPriorityEnum priority;

    private Long creatorId;

    @NotNull(groups = Marker.OnCreate.class)
    private Long executorId;

    private List<CommentDto> comments;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
}
