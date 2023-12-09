package org.elgordogato.taskmanagementsystem.controllers;


import jakarta.validation.Valid;
import org.elgordogato.taskmanagementsystem.dtos.LoginResponse;
import org.elgordogato.taskmanagementsystem.dtos.LoginUserDto;
import org.elgordogato.taskmanagementsystem.dtos.RegisterUserDto;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.services.JwtService;
import org.elgordogato.taskmanagementsystem.services.impl.AuthenticationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public RegisterUserDto register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        UserEntity registeredUser = authenticationService.signup(registerUserDto);

        return RegisterUserDto.builder()
                .email(registeredUser.getEmail())
                .fullName(registeredUser.getName())
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
        UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        System.out.println(new LoginResponse(jwtToken, jwtService.getExpirationTime()));


        return ResponseEntity.ok(new LoginResponse(jwtToken, jwtService.getExpirationTime()));
    }
}