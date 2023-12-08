package org.elgordogato.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import org.elgordogato.taskmanagementsystem.dto.LoginUserDto;
import org.elgordogato.taskmanagementsystem.dto.RegisterUserDto;
import org.elgordogato.taskmanagementsystem.entity.UserEntity;
import org.elgordogato.taskmanagementsystem.response.LoginResponse;
import org.elgordogato.taskmanagementsystem.service.AuthenticationService;
import org.elgordogato.taskmanagementsystem.service.JwtService;
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

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<RegisterUserDto> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        RegisterUserDto registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
        String authenticatedUserId = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUserId);

        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}