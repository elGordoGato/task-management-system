package org.elgordogato.taskmanagementsystem.repositories;

import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long>, TaskRepositoryCustom {
}
