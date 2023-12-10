package org.elgordogato.taskmanagementsystem.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.elgordogato.taskmanagementsystem.utils.enums.TaskPriorityEnum;
import org.elgordogato.taskmanagementsystem.utils.enums.TaskStatusEnum;

@Builder
@Getter
@ToString
public class TaskParameters {
    private String title;

    private String description;

    private TaskStatusEnum status;

    private TaskPriorityEnum priority;

    private Long creatorId;

    private Long executorId;

    private Boolean hasComments;
}
