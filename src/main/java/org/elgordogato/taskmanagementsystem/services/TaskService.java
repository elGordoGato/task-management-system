package org.elgordogato.taskmanagementsystem.services;

import org.elgordogato.taskmanagementsystem.dtos.TaskDto;
import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.utils.TaskParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface TaskService {
    TaskEntity create(TaskDto inputTask, UserEntity creator, UserEntity executor);

    TaskEntity update(TaskDto inputTask, Long creator, UserEntity executor);

    @Transactional
    void delete(Long taskID, Long requester);

    TaskEntity getByIdWithComments(Long taskID);

    Page<TaskEntity> getByParameters(TaskParameters parameters, Pageable page);
}
