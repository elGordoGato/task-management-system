package org.elgordogato.taskmanagementsystem.dtos.mapper;

import org.elgordogato.taskmanagementsystem.dtos.UserDto;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;

public class UserMapper {
    public static UserDto dtoFromEntity(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .build();
    }
}
