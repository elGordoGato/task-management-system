package org.elgordogato.taskmanagementsystem.services.impl;


import lombok.RequiredArgsConstructor;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.exceptions.NotFoundException;
import org.elgordogato.taskmanagementsystem.repositories.UserRepository;
import org.elgordogato.taskmanagementsystem.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity getById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(UserEntity.class, userId));
    }
}
