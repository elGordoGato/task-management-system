package org.elgordogato.taskmanagementsystem.services.taskService;

import org.elgordogato.taskmanagementsystem.dtos.TaskDto;
import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.utils.TaskParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskEntity create(TaskDto inputTask, UserEntity creator, UserEntity executor);

    TaskEntity update(TaskDto inputTask, Long creator, UserEntity executor);

    void delete(Long taskID, Long requester);

    TaskEntity getById(Long taskID);

    Page<TaskEntity> getByParameters(TaskParameters parameters, Pageable page);
}
