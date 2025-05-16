package com.example.geocontact.service;

import com.example.geocontact.dto.LoginRequest;
import com.example.geocontact.dto.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> signup(SignupRequest request);
    ResponseEntity<?> signin(LoginRequest request);
}