package org.elgordogato.taskmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.elgordogato.taskmanagementsystem.dto.LoginUserDto;
import org.elgordogato.taskmanagementsystem.dto.RegisterUserDto;
import org.elgordogato.taskmanagementsystem.entity.UserEntity;
import org.elgordogato.taskmanagementsystem.mapper.UserMapper;
import org.elgordogato.taskmanagementsystem.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    public RegisterUserDto signup(RegisterUserDto input) {
        UserEntity user = new UserEntity();
                user.setName(input.getName());
                user.setEmail(input.getEmail());
                user.setPassword(input.getPassword());

        return UserMapper.dtoFromEntity(userRepository.save(user));
    }

    public String authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(input.getEmail()))
                .getId().toString();
    }

}
