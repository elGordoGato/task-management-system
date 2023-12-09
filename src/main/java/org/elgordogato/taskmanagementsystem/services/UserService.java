package org.elgordogato.taskmanagementsystem.services;

import org.elgordogato.taskmanagementsystem.entities.UserEntity;

public interface UserService {
    UserEntity getById(Long userId);
}
