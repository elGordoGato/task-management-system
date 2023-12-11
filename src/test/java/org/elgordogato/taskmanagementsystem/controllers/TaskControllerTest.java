package org.elgordogato.taskmanagementsystem.controllers;

import org.elgordogato.taskmanagementsystem.dtos.TaskDto;
import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.services.authenticationService.JwtService;
import org.elgordogato.taskmanagementsystem.services.taskService.TaskService;
import org.elgordogato.taskmanagementsystem.services.userService.UserService;
import org.elgordogato.taskmanagementsystem.utils.enums.TaskPriorityEnum;
import org.elgordogato.taskmanagementsystem.utils.enums.TaskStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureJsonTesters
@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {
    @Autowired
    private JacksonTester<TaskDto> jsonTask;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;
    @MockBean
    private TaskService taskService;
    @MockBean
    private JwtService jwtService;
    private TaskDto inputTaskDto;
    private TaskEntity createdTask;
    private UserEntity currentUser;
    private UserEntity executor;

    @BeforeEach
    public void setUp() {

        inputTaskDto = new TaskDto();
        inputTaskDto.setTitle("Task 1");
        inputTaskDto.setDescription("Description 1");
        inputTaskDto.setExecutorId(101L);

        createdTask = new TaskEntity();
        createdTask.setId(1L);
        createdTask.setTitle("Task 1");
        createdTask.setDescription("Description 1");

        currentUser = new UserEntity();
        currentUser.setId(100L);
        currentUser.setFullName("User 1");
        createdTask.setCreator(currentUser);

        executor = new UserEntity();
        executor.setId(101L);
        executor.setFullName("User 2");
        createdTask.setExecutor(executor);

        Authentication authentication = mock(Authentication.class);
        given(authentication.getPrincipal()).willReturn(currentUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testCreateTaskSuccess() throws Exception {
        // given
        given(userService.getById(anyLong()))
                .willReturn(executor);
        given(taskService.create(inputTaskDto, currentUser, executor))
                .willReturn(createdTask);

        // when
        MockHttpServletResponse response = mvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTask.write(inputTaskDto).getJson()))
                .andReturn().getResponse();

        // then
        verify(userService).getById(101L);
        verify(taskService).create(inputTaskDto, currentUser, executor);
        assertEquals(201, response.getStatus());
        inputTaskDto.setId(1L);
        inputTaskDto.setStatus(TaskStatusEnum.PENDING);
        inputTaskDto.setPriority(TaskPriorityEnum.LOW);
        inputTaskDto.setCreatorId(currentUser.getId());
        assertEquals(jsonTask.write(inputTaskDto).getJson(), response.getContentAsString());
    }
}