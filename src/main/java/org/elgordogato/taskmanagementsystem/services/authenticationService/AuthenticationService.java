package org.elgordogato.taskmanagementsystem.services.authenticationService;

import org.elgordogato.taskmanagementsystem.dtos.UserDto;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;

public interface AuthenticationService {

    UserEntity signup(UserDto input);


    UserEntity authenticate(UserDto input);
}
