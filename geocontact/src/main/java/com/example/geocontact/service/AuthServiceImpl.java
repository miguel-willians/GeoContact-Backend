package com.example.geocontact.service;

import com.example.geocontact.dto.LoginRequest;
import com.example.geocontact.dto.SignupRequest;
import com.example.geocontact.dto.JwtResponse;
import com.example.geocontact.entity.User;
import com.example.geocontact.repository.UserRepository;
import com.example.geocontact.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email já está em uso");
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    @Override
    public ResponseEntity<?> signin(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }

        // Gera o token JWT
        String token = jwtUtils.generateToken(user.getId().toString());

        // Retorna o token dentro do DTO JwtResponse
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
