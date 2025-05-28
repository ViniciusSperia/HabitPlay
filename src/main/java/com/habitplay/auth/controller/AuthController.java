package com.habitplay.auth.controller;

import com.habitplay.auth.dto.request.AuthRequest;
import com.habitplay.auth.dto.response.AuthResponse;
import com.habitplay.auth.dto.request.RegisterRequest;
import com.habitplay.auth.dto.request.AuthRefreshRequest;
import com.habitplay.auth.service.AuthService;
import com.habitplay.config.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody @Valid AuthRefreshRequest request) {
        String email = jwtService.extractUsername(request.getToken());

        if (!jwtService.isTokenValid(request.getToken(), email)) {
            return ResponseEntity.status(401).build();
        }

        String newToken = jwtService.generateToken(email);
        return ResponseEntity.ok(new AuthResponse(newToken));
    }
}
