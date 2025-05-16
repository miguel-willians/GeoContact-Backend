package com.example.geocontact.controller;


import com.example.geocontact.dto.LoginRequest;
import com.example.geocontact.dto.SignupRequest;
import com.example.geocontact.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUp) {
        return authService.signup(signUp);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest login) {
        return authService.signin(login);
    }
}