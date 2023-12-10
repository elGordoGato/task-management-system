package org.elgordogato.taskmanagementsystem.controllers;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.elgordogato.taskmanagementsystem.dtos.LoginResponse;
import org.elgordogato.taskmanagementsystem.dtos.UserDto;
import org.elgordogato.taskmanagementsystem.dtos.mapper.UserMapper;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.services.authenticationService.JwtService;
import org.elgordogato.taskmanagementsystem.services.authenticationService.AuthenticationServiceImpl;
import org.elgordogato.taskmanagementsystem.utils.Marker;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationServiceImpl authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationServiceImpl authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    @Validated(Marker.OnCreate.class)
    public UserDto register(@RequestBody @Valid UserDto userDto) {
        log.info("Received request to register new user\nemail: {}\nname: {}",
                userDto.getEmail(), userDto.getFullName());
        UserEntity registeredUser = authenticationService.signup(userDto);

        return UserMapper.dtoFromEntity(registeredUser);
    }

    @PostMapping("/login")
    public LoginResponse authenticate(@RequestBody @Valid UserDto loginUserDto) {
        log.info("Received request to login with email: {}", loginUserDto.getEmail());
        UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        System.out.println(new LoginResponse(jwtToken, jwtService.getExpirationTime()));


        return new LoginResponse(jwtToken, jwtService.getExpirationTime());
    }
}