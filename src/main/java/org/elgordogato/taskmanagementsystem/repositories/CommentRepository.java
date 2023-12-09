package org.elgordogato.taskmanagementsystem.repositories;

import org.elgordogato.taskmanagementsystem.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
