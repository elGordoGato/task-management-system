package org.elgordogato.taskmanagementsystem.dtos.mapper;

import org.elgordogato.taskmanagementsystem.dtos.TaskDto;
import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.springframework.data.domain.Page;

public class TaskMapper {
    public static TaskDto dtoFromEntity(TaskEntity entity) {
        return TaskDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .priority(entity.getPriority())
                .creatorId(entity.getCreator().getId())
                .executorId(entity.getExecutor().getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static Page<TaskDto> pageDtoFromEntity(Page<TaskEntity> foundTasks) {
        return foundTasks.map(entity -> {
            TaskDto dto = dtoFromEntity(entity);
            dto.setComments(CommentMapper.listDtoFromEntity(entity.getComments()));
            return dto;
        });
    }
}
