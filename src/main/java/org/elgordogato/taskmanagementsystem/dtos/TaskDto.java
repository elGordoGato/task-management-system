package org.elgordogato.taskmanagementsystem.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.elgordogato.taskmanagementsystem.utils.enums.TaskPriorityEnum;
import org.elgordogato.taskmanagementsystem.utils.enums.TaskStatusEnum;
import org.elgordogato.taskmanagementsystem.utils.Marker.OnCreate;
import org.elgordogato.taskmanagementsystem.utils.Marker.OnUpdate;

import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
public class TaskDto {
    @NotNull(groups = OnUpdate.class)
    private Long id;

    @NotNull(groups = OnCreate.class)
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @Size(max = 200,
            groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @Size(max = 2000,
            groups = {OnCreate.class, OnUpdate.class})
    private String description;

    private TaskStatusEnum status;

    private TaskPriorityEnum priority;

    private Long creatorId;

    @NotNull(groups = OnCreate.class)
    private Long executorId;

    @JsonInclude(NON_NULL)
    private List<CommentDto> comments;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
}
