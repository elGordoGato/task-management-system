package org.elgordogato.taskmanagementsystem.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elgordogato.taskmanagementsystem.dtos.TaskDto;
import org.elgordogato.taskmanagementsystem.dtos.mapper.TaskMapper;
import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.services.taskService.TaskService;
import org.elgordogato.taskmanagementsystem.services.userService.UserService;
import org.elgordogato.taskmanagementsystem.utils.Marker.OnCreate;
import org.elgordogato.taskmanagementsystem.utils.Marker.OnUpdate;
import org.elgordogato.taskmanagementsystem.utils.TaskParameters;
import org.elgordogato.taskmanagementsystem.utils.enums.SortParamEnum;
import org.elgordogato.taskmanagementsystem.utils.enums.TaskPriorityEnum;
import org.elgordogato.taskmanagementsystem.utils.enums.TaskStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public TaskDto create(@RequestBody @Valid TaskDto inputTaskDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        log.info("Received request from user: {} to create task: {}", currentUser.getId(), inputTaskDto);

        UserEntity executor = userService.getById(inputTaskDto.getExecutorId());


        TaskEntity createdTask = taskService.create(inputTaskDto, currentUser, executor);

        return TaskMapper.dtoFromEntity(createdTask);
    }

    @PatchMapping
    @Validated(OnUpdate.class)
    public TaskDto update(@RequestBody @Valid TaskDto inputTaskDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long requesterId = ((UserEntity) authentication.getPrincipal()).getId();

        log.info("Received request from user: {} to update task with new data: {}", requesterId, inputTaskDto);

        UserEntity executor = Optional.of(inputTaskDto.getExecutorId())
                .map(userService::getById)
                .orElse(null);


        TaskEntity updatedTask = taskService.update(inputTaskDto, requesterId, executor);

        return TaskMapper.dtoFromEntity(updatedTask);
    }

    @DeleteMapping(("/{taskId}"))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Long requesterId = ((UserEntity) authentication.getPrincipal()).getId();

        log.info("Received request to delete task with id: {} from user: {} ", taskId, requesterId);

        taskService.delete(taskId, requesterId);
    }

    @GetMapping
    public Page<TaskDto> getTasks(@RequestParam(value = "title", required = false)
                                  @Size(min = 1, max = 200) String title,

                                  @RequestParam(value = "description", required = false)
                                  @Size(min = 1, max = 2000) String description,

                                  @RequestParam(value = "status", required = false)
                                  TaskStatusEnum status,

                                  @RequestParam(value = "priority", required = false)
                                  TaskPriorityEnum priority,

                                  @RequestParam(value = "creatorId", required = false)
                                  Long creatorId,

                                  @RequestParam(value = "executorId", required = false)
                                  Long executorId,

                                  @RequestParam(value = "hasComments", required = false)
                                  Boolean hasComments,

                                  @RequestParam(value = "sortParam", defaultValue = "CREATED")
                                  SortParamEnum sortParam,

                                  @RequestParam(value = "sortDirection", defaultValue = "ASC")
                                  String sortDirection,

                                  @RequestParam(value = "pageNumber", defaultValue = "0")
                                  @PositiveOrZero Integer pageNumber,

                                  @RequestParam(value = "size", defaultValue = "10")
                                  @Positive Integer size) {

        Sort sort = Sort.by(
                Sort.Direction.valueOf(sortDirection),
                sortParam.getFieldName());
        Pageable page = PageRequest.of(pageNumber, size, sort);

        TaskParameters parameters = TaskParameters.builder()
                .title(title)
                .description(description)
                .status(status)
                .priority(priority)
                .creatorId(creatorId)
                .executorId(executorId)
                .hasComments(hasComments)
                .build();

        log.info("Received request to get tasks with parameters: {}\nand page: {} ", parameters, page);

        Page<TaskEntity> foundTasks = taskService.getByParameters(parameters, page);

        return TaskMapper.pageDtoFromEntity(foundTasks);
    }
}
