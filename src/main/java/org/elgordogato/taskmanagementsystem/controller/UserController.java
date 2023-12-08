package org.elgordogato.taskmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.elgordogato.taskmanagementsystem.entity.UserEntity;
import org.elgordogato.taskmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserEntity> authenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        System.out.println(currentUser);

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> allUsers() {
        List<UserEntity> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
}
