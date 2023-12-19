package org.elgordogato.taskmanagementsystem.repositories;


import org.elgordogato.taskmanagementsystem.dtos.UserIdNameDto;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Page<UserIdNameDto> findAllProjectedBy(Pageable pageable);
}
