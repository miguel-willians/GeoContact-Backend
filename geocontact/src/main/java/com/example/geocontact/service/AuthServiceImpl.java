package com.example.geocontact.service;

import com.example.geocontact.dto.LoginRequest;
import com.example.geocontact.dto.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public ResponseEntity<?> signup(SignupRequest request) {
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    @Override
    public ResponseEntity<?> signin(LoginRequest request) {
        return ResponseEntity.ok("Usuário autenticado com sucesso!");
    }
}
