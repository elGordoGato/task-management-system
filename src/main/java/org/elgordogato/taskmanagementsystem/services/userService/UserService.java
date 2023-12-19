package org.elgordogato.taskmanagementsystem.services.userService;

import org.elgordogato.taskmanagementsystem.dtos.UserIdNameDto;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserEntity getById(Long userId);

    Page<UserIdNameDto> getAllShort(Pageable page);
}
