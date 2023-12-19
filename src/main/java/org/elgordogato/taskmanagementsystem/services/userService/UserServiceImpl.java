package org.elgordogato.taskmanagementsystem.services.userService;


import lombok.RequiredArgsConstructor;
import org.elgordogato.taskmanagementsystem.dtos.UserIdNameDto;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.exceptions.NotFoundException;
import org.elgordogato.taskmanagementsystem.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(UserEntity.class, userId));
    }

    @Override
    public Page<UserIdNameDto> getAllShort(Pageable page) {
        return userRepository.findAllProjectedBy(page);
    }
}
