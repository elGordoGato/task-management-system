package org.elgordogato.taskmanagementsystem.services.commentService;

import org.elgordogato.taskmanagementsystem.dtos.CommentDto;
import org.elgordogato.taskmanagementsystem.entities.CommentEntity;
import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.exceptions.ForbiddenException;
import org.elgordogato.taskmanagementsystem.exceptions.NotFoundException;
import org.elgordogato.taskmanagementsystem.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private CommentDto inputComment;
    private CommentEntity createdComment;
    private CommentEntity commentToUpdate;
    private TaskEntity task;
    private UserEntity currentUser;


    @BeforeEach
    public void setUp() {
        inputComment = CommentDto.builder().id(1L).text("This is a comment").build();

        task = new TaskEntity();
        task.setId(1L);
        task.setTitle("Task 1");

        currentUser = new UserEntity();
        currentUser.setId(100L);
        currentUser.setFullName("User 1");

        createdComment = new CommentEntity();
        createdComment.setText(inputComment.getText());
        createdComment.setTask(task);
        createdComment.setAuthor(currentUser);

        commentToUpdate = new CommentEntity();
        commentToUpdate.setId(1L);
        commentToUpdate.setText("This is an old comment");
        commentToUpdate.setAuthor(currentUser);
    }

    @Test
    public void testCreateCommentSuccess() {
        // given
        when(commentRepository.save(any(CommentEntity.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // when
        CommentEntity result = commentService.create(inputComment, task, currentUser);

        // then
        verify(commentRepository).save(any(CommentEntity.class));
        assertEquals(createdComment.getText(), result.getText());
        assertEquals(createdComment.getAuthor(), result.getAuthor());
        assertEquals(createdComment.getTask(), result.getTask());
    }

    @Test
    public void testUpdateCommentSuccess() {
        // given
        when(commentRepository.findById(1L)).thenReturn(java.util.Optional.of(commentToUpdate));
        when(commentRepository.save(any(CommentEntity.class))).thenAnswer(i -> i.getArgument(0));

        // when
        CommentEntity result = commentService.update(inputComment, currentUser);

        // then
        verify(commentRepository).findById(1L);
        verify(commentRepository).save(any(CommentEntity.class));
        assertEquals(inputComment.getText(), result.getText());
    }

    @Test
    public void testUpdateCommentNotFound() {
        // given
        when(commentRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () -> commentService.update(inputComment, currentUser));

        // then
        verify(commentRepository).findById(1L);
        assertEquals("CommentEntity with id: 1 was not found", exception.getMessage());
    }

    @Test
    public void testUpdateCommentForbidden() {
        // given
        when(commentRepository.findById(1L)).thenReturn(java.util.Optional.of(commentToUpdate));
        UserEntity wrongUser = new UserEntity();
        wrongUser.setId(101L);

        // when
        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> commentService.update(inputComment, wrongUser));

        // then
        verify(commentRepository).findById(1L);
        assertEquals("User with id: 101 has no rights to update comment with id: 1", exception.getMessage());
    }


}
