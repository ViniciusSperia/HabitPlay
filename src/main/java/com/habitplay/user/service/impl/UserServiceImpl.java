package com.habitplay.user.service.impl;

import com.habitplay.user.dto.request.UserUpdateRequest;
import com.habitplay.user.dto.response.UserResponse;
import com.habitplay.user.model.User;
import com.habitplay.user.repository.UserRepository;
import com.habitplay.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getProfile(User user) {
        return UserResponse.from(user);
    }

    @Override
    @Transactional
    public UserResponse updateProfile(User user, UserUpdateRequest request) {
        user.setFullName(request.getFullName());
        user.setProfileImageUrl(request.getProfileImageUrl());
        return UserResponse.from(userRepository.save(user));
    }

    @Override
    @Transactional
    public void softDeleteProfile(User user) {
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> listAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public void toggleStatus(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setActive(!user.isActive());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void adminSoftDelete(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setActive(false);
        userRepository.save(user);
    }
}
