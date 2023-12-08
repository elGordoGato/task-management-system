package org.elgordogato.taskmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.elgordogato.taskmanagementsystem.entity.UserEntity;
import org.elgordogato.taskmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    public List<UserEntity> allUsers() {
        return repository.findAll();
    }
}
