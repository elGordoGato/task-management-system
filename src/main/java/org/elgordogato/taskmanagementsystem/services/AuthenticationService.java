package org.elgordogato.taskmanagementsystem.services;

import org.elgordogato.taskmanagementsystem.dtos.LoginUserDto;
import org.elgordogato.taskmanagementsystem.dtos.RegisterUserDto;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;

public interface AuthenticationService {

    UserEntity signup(RegisterUserDto input);

    UserEntity authenticate(LoginUserDto input);
}
