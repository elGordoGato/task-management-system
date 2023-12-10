package org.elgordogato.taskmanagementsystem.services;

import org.elgordogato.taskmanagementsystem.dtos.LoginUserDto;
import org.elgordogato.taskmanagementsystem.dtos.UserDto;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;

public interface AuthenticationService {

    UserEntity signup(UserDto input);


    UserEntity authenticate(UserDto input);
}
