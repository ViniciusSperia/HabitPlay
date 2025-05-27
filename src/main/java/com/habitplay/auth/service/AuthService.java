package com.habitplay.auth.service;

import com.habitplay.auth.dto.request.AuthRequest;
import com.habitplay.auth.dto.response.AuthResponse;
import com.habitplay.auth.dto.request.RegisterRequest;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);
    AuthResponse register(RegisterRequest request);
}