package org.elgordogato.taskmanagementsystem.controllers;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elgordogato.taskmanagementsystem.dtos.UserDto;
import org.elgordogato.taskmanagementsystem.dtos.UserIdNameDto;
import org.elgordogato.taskmanagementsystem.dtos.mapper.UserMapper;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.services.userService.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/me")
    public UserDto authenticatedUser() {
        log.info("Received request to get personal info");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return UserMapper.dtoFromEntity(currentUser);
    }

    @GetMapping
    public Page<UserIdNameDto> allUsers(@RequestParam(value = "pageNumber", defaultValue = "0")
                                        @PositiveOrZero Integer pageNumber,

                                        @RequestParam(value = "size", defaultValue = "10")
                                        @Positive Integer size) {
        Pageable pageable = PageRequest.of(pageNumber, size);

        log.info("Received request to get page #{} with size: {} of all users",
                pageNumber, size);

        return userService.getAllShort(pageable);
    }

}