package org.elgordogato.taskmanagementsystem.services;

import org.elgordogato.taskmanagementsystem.dtos.CommentDto;
import org.elgordogato.taskmanagementsystem.entities.CommentEntity;
import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;

public interface CommentService {
    CommentEntity create(CommentDto inputComment, TaskEntity task, UserEntity currentUser);

    CommentEntity update(CommentDto inputComment, UserEntity currentUser);

    void delete(Long commentId, UserEntity currentUser);
}
