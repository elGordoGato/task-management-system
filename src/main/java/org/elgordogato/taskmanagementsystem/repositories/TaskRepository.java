package org.elgordogato.taskmanagementsystem.repositories;

import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long>, TaskRepositoryCustom {
    @EntityGraph(value = "with-comments")
    Optional<TaskEntity> findWithCommentsById(Long eventId);
}
