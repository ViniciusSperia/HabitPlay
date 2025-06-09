package com.habitplay.auth.service.impl;

import com.habitplay.auth.dto.request.AuthRequest;
import com.habitplay.auth.dto.request.RegisterRequest;
import com.habitplay.auth.dto.response.AuthResponse;
import com.habitplay.auth.service.AuthService;
import com.habitplay.config.exception.NotFoundException;
import com.habitplay.config.security.JwtService;
import com.habitplay.user.model.Role;
import com.habitplay.user.model.User;
import com.habitplay.user.repository.RoleRepository;
import com.habitplay.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final UUID DEFAULT_ROLE_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getEmail());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        Role defaultRole = roleRepository.findById(DEFAULT_ROLE_ID)
                .orElseThrow(() -> new NotFoundException("Default USER role not found."));

        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .profileImageUrl(request.getProfileImageUrl())
                .role(defaultRole)
                .active(true)
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}
