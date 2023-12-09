package org.elgordogato.taskmanagementsystem.repositories;

import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.elgordogato.taskmanagementsystem.utils.TaskParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskRepositoryCustom {

    Page<TaskEntity> findAllByParams(TaskParameters parameters, Pageable page);
}
