package org.elgordogato.taskmanagementsystem.mapper;

import org.elgordogato.taskmanagementsystem.dto.RegisterUserDto;
import org.elgordogato.taskmanagementsystem.entity.UserEntity;

public class UserMapper {
    public static RegisterUserDto dtoFromEntity(UserEntity user){
        return RegisterUserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
