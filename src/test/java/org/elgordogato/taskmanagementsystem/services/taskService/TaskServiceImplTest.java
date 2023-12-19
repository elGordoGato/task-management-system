package org.elgordogato.taskmanagementsystem.services.taskService;

import org.elgordogato.taskmanagementsystem.dtos.TaskDto;
import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.exceptions.ForbiddenException;
import org.elgordogato.taskmanagementsystem.exceptions.NotFoundException;
import org.elgordogato.taskmanagementsystem.repositories.TaskRepository;
import org.elgordogato.taskmanagementsystem.utils.TaskParameters;
import org.elgordogato.taskmanagementsystem.utils.enums.TaskPriorityEnum;
import org.elgordogato.taskmanagementsystem.utils.enums.TaskStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskEntity task;

    @BeforeEach
    public void setUp() {
        UserEntity creator = new UserEntity();
        creator.setId(100L);
        task = new TaskEntity();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setCreator(creator);
    }

    @Test
    void create() {
        // given
        TaskDto inputTask = TaskDto.builder()
                .title("Test task")
                .description("This is a test task")
                .status(TaskStatusEnum.PENDING)
                .priority(TaskPriorityEnum.HIGH).build();

        UserEntity creator = new UserEntity();
        creator.setId(1L);
        creator.setFullName("Alice");

        UserEntity executor = new UserEntity();
        executor.setId(2L);
        executor.setFullName("Bob");

        TaskEntity expectedTask = new TaskEntity();
        expectedTask.setId(1L);
        expectedTask.setTitle(inputTask.getTitle());
        expectedTask.setDescription(inputTask.getDescription());
        expectedTask.setStatus(inputTask.getStatus());
        expectedTask.setPriority(inputTask.getPriority());
        expectedTask.setCreator(creator);
        expectedTask.setExecutor(executor);

        when(taskRepository.save(any(TaskEntity.class))).thenReturn(expectedTask);

        // when
        TaskEntity actualTask = taskService.create(inputTask, creator, executor);

        // then
        assertEquals(expectedTask, actualTask);
    }


    @Test
    void updateByCreatorShouldUpdateTaskFields() {
        // given
        TaskDto inputTask = TaskDto.builder()
                .id(1L)
                .title("New title")
                .description("New description")
                .status(TaskStatusEnum.IN_PROGRESS)
                .priority(TaskPriorityEnum.HIGH).build();

        UserEntity creator = new UserEntity();
        creator.setId(1L);

        UserEntity executor = new UserEntity();
        executor.setId(2L);

        TaskEntity existingTask = new TaskEntity();
        existingTask.setId(1L);
        existingTask.setCreator(creator);
        existingTask.setExecutor(creator);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(TaskEntity.class))).thenAnswer(i -> i.getArgument(0));

        // when
        TaskEntity updatedTask = taskService.update(inputTask, 1L, executor);

        // then
        verify(taskRepository).save(existingTask);
        assertEquals("New title", updatedTask.getTitle());
        assertEquals("New description", updatedTask.getDescription());
        assertEquals(TaskStatusEnum.IN_PROGRESS, updatedTask.getStatus());
        assertEquals(TaskPriorityEnum.HIGH, updatedTask.getPriority());
        assertEquals(executor, updatedTask.getExecutor());
    }

    @Test
    void updateByExecutorShouldUpdateTaskStatus() {
        // given
        TaskDto inputTask = new TaskDto();
        inputTask.setId(1L);
        inputTask.setStatus(TaskStatusEnum.COMPLETED);

        UserEntity creator = new UserEntity();
        creator.setId(2L);

        UserEntity executor = new UserEntity();
        executor.setId(1L);

        TaskEntity existingTask = new TaskEntity();
        existingTask.setId(1L);
        existingTask.setCreator(creator);
        existingTask.setExecutor(executor);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(TaskEntity.class))).thenAnswer(i -> i.getArgument(0));

        // when
        TaskEntity updatedTask = taskService.update(inputTask, 1L, null);

        // then
        verify(taskRepository).save(existingTask);
        assertEquals(TaskStatusEnum.COMPLETED, updatedTask.getStatus());
    }

    @Test
    void updateShouldThrowNotFoundExceptionWhenTaskNotFound() {
        // given
        TaskDto inputTask = new TaskDto();
        inputTask.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        assertThrows(NotFoundException.class, () -> taskService.update(inputTask, 1L, null));

        // then
        verify(taskRepository, never()).save(any());
    }

    @Test
    void updateShouldThrowForbiddenExceptionWhenRequesterIsNotCreatorOrExecutor() {
        // given
        TaskDto inputTask = new TaskDto();
        inputTask.setId(1L);

        TaskEntity existingTask = new TaskEntity();
        existingTask.setId(1L);
        existingTask.setCreator(new UserEntity());
        existingTask.setExecutor(new UserEntity());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        // when
        assertThrows(ForbiddenException.class, () -> taskService.update(inputTask, 3L, null));

        // then
        verify(taskRepository, never()).save(any());
    }


    @Test
    public void testDeleteTaskSuccess() {
        // given
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // when
        taskService.delete(1L, 100L);

        // then
        verify(taskRepository).delete(task);
    }

    @Test
    public void testDeleteTaskNotFound() {
        // given
        when(taskRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () -> taskService.delete(1L, 100L));

        // then
        assertEquals("TaskEntity with id: 1 was not found", exception.getMessage());
    }

    @Test
    public void testDeleteTaskForbidden() {
        // given
        when(taskRepository.findById(1L)).thenReturn(java.util.Optional.of(task));

        // when
        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> taskService.delete(1L, 101L));

        // then
        assertEquals("User with id: 101 has no rights to delete task with id: 1", exception.getMessage());
    }


    @Test
    public void testGetWithCommentsByIdSuccess() {
        // given
        when(taskRepository.findWithCommentsById(1L)).thenReturn(java.util.Optional.of(task));

        // when
        TaskEntity result = taskService.getById(1L);

        // then
        assertEquals(task, result);
    }

    @Test
    public void testGetWithCommentsByIdNotFound() {
        // given
        when(taskRepository.findWithCommentsById(1L)).thenReturn(java.util.Optional.empty());

        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () -> taskService.getById(1L));

        // then
        assertEquals("TaskEntity with id: 1 was not found", exception.getMessage());
    }

    @Test
    public void testGetByParametersSuccess() {
        // given
        TaskParameters parameters = TaskParameters.builder()
                .title("Task 1")
                .build();
        Pageable page = Pageable.unpaged();
        Page<TaskEntity> expectedPage = new PageImpl<>(Collections.singletonList(task));
        when(taskRepository.findAllByParams(parameters, page)).thenReturn(expectedPage);

        // when
        Page<TaskEntity> resultPage = taskService.getByParameters(parameters, page);

        // then
        assertEquals(expectedPage, resultPage);
    }
}