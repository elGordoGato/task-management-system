package org.elgordogato.taskmanagementsystem.services.commentService;

import lombok.RequiredArgsConstructor;
import org.elgordogato.taskmanagementsystem.dtos.CommentDto;
import org.elgordogato.taskmanagementsystem.entities.CommentEntity;
import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.exceptions.ForbiddenException;
import org.elgordogato.taskmanagementsystem.exceptions.NotFoundException;
import org.elgordogato.taskmanagementsystem.repositories.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public CommentEntity create(CommentDto inputComment, TaskEntity task, UserEntity currentUser) {

        CommentEntity createdComment = new CommentEntity();
        createdComment.setText(inputComment.getText());
        createdComment.setTask(task);
        createdComment.setAuthor(currentUser);

        return commentRepository.save(createdComment);
    }

    @Override
    public CommentEntity update(CommentDto inputComment, UserEntity currentUser) {

        CommentEntity commentToUpdate = commentRepository.findById(inputComment.getId())
                .orElseThrow(() ->
                        new NotFoundException(CommentEntity.class, inputComment.getId()));
        if (!commentToUpdate.getAuthor().getId().equals(currentUser.getId())) {
            throw new ForbiddenException(currentUser.getId(), "update comment", inputComment.getId());
        }
        commentToUpdate.setText(inputComment.getText());

        return commentRepository.save(commentToUpdate);
    }

    @Override
    public void delete(Long commentId, UserEntity currentUser) {
        CommentEntity commentToDelete = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new NotFoundException(CommentEntity.class, commentId));

        if (!commentToDelete.getAuthor().getId().equals(currentUser.getId())) {
            throw new ForbiddenException(currentUser.getId(), "delete comment", commentId);
        }

        commentRepository.delete(commentToDelete);
    }
}
