package org.elgordogato.taskmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elgordogato.taskmanagementsystem.dtos.CommentDto;
import org.elgordogato.taskmanagementsystem.dtos.mapper.CommentMapper;
import org.elgordogato.taskmanagementsystem.entities.CommentEntity;
import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.services.commentService.CommentService;
import org.elgordogato.taskmanagementsystem.services.taskService.TaskService;
import org.elgordogato.taskmanagementsystem.utils.Marker;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RequestMapping(path = "/tasks/{taskId}/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(Marker.OnCreate.class)
    public CommentDto create(@PathVariable Long taskId,
                             @RequestBody @Valid CommentDto inputComment) {
        log.info("Received request to post new comment for task with id: {}\n{}",
                taskId, inputComment);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        TaskEntity task = taskService.getById(taskId);

        CommentEntity createdComment = commentService.create(inputComment, task, currentUser);

        return CommentMapper.dtoFromEntity(createdComment);
    }

    @PatchMapping
    @Validated(Marker.OnUpdate.class)
    public CommentDto update(@RequestBody @Valid CommentDto inputComment) {
        log.info("Received request to update comment with id: {}\nnew data: {}",
                inputComment.getId(), inputComment);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        CommentEntity updatedComment = commentService.update(inputComment, currentUser);

        return CommentMapper.dtoFromEntity(updatedComment);
    }

    @DeleteMapping(("/{commentId}"))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long commentId) {
        log.info("Received request to delete comment with id: {}", commentId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        commentService.delete(commentId, currentUser);
    }
}
