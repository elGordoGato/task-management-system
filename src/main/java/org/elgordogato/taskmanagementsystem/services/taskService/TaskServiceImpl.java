package org.elgordogato.taskmanagementsystem.services.taskService;

import lombok.RequiredArgsConstructor;
import org.elgordogato.taskmanagementsystem.dtos.TaskDto;
import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.exceptions.ForbiddenException;
import org.elgordogato.taskmanagementsystem.exceptions.NotFoundException;
import org.elgordogato.taskmanagementsystem.repositories.TaskRepository;
import org.elgordogato.taskmanagementsystem.utils.TaskParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public TaskEntity create(TaskDto inputTask, UserEntity creator, UserEntity executor) {
        TaskEntity createdTask = new TaskEntity();

        createdTask.setTitle(inputTask.getTitle());
        createdTask.setDescription(inputTask.getDescription());
        Optional.ofNullable(inputTask.getStatus())
                .ifPresent(createdTask::setStatus);
        Optional.ofNullable(inputTask.getPriority())
                .ifPresent(createdTask::setPriority);
        createdTask.setCreator(creator);
        createdTask.setExecutor(executor);

        return taskRepository.save(createdTask);
    }

    @Override
    @Transactional
    public TaskEntity update(TaskDto inputTask, Long requesterId, UserEntity executor) {
        TaskEntity taskToUpdate = taskRepository.findById(inputTask.getId())
                .orElseThrow(() ->
                        new NotFoundException(TaskEntity.class, inputTask.getId()));

        if (requesterId.equals(taskToUpdate.getCreator().getId())) {
            updateByCreator(taskToUpdate, inputTask, executor);

        } else if (requesterId.equals(taskToUpdate.getExecutor().getId())) {
            updateByExecutor(taskToUpdate, inputTask);

        } else throw new ForbiddenException(requesterId, "update task", inputTask.getId());

        return taskRepository.save(taskToUpdate);
    }

    @Override
    @Transactional
    public void delete(Long taskID, Long requesterId) {
        TaskEntity taskToDelete = taskRepository.findById(taskID)
                .orElseThrow(() ->
                        new NotFoundException(TaskEntity.class, taskID));

        if (!taskToDelete.getCreator().getId().equals(requesterId)) {
            throw new ForbiddenException(requesterId, "delete task", taskID);
        }
        taskRepository.delete(taskToDelete);
    }

    @Override
    public TaskEntity getWithCommentsById(Long taskID) {
        return taskRepository.findWithCommentsById(taskID)
                .orElseThrow(() ->
                        new NotFoundException(TaskEntity.class, taskID));
    }

    @Override
    public Page<TaskEntity> getByParameters(TaskParameters parameters, Pageable page) {
        return taskRepository.findAllByParams(parameters, page);
    }

    private void updateByCreator(TaskEntity taskToBeUpdated, TaskDto inputTask, UserEntity executor) {
        Optional.ofNullable(inputTask.getTitle()).ifPresent(taskToBeUpdated::setTitle);
        Optional.ofNullable(inputTask.getDescription()).ifPresent(taskToBeUpdated::setDescription);
        Optional.ofNullable(inputTask.getStatus()).ifPresent(taskToBeUpdated::setStatus);
        Optional.ofNullable(inputTask.getPriority()).ifPresent(taskToBeUpdated::setPriority);
        Optional.ofNullable(executor).ifPresent(taskToBeUpdated::setExecutor);
    }

    private void updateByExecutor(TaskEntity taskToBeUpdated, TaskDto inputTask) {
        Optional.ofNullable(inputTask.getStatus()).ifPresent(taskToBeUpdated::setStatus);
    }
}
