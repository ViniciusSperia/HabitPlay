package com.habitplay.user.service;

import com.habitplay.user.dto.request.UserUpdateRequest;
import com.habitplay.user.dto.response.UserResponse;
import com.habitplay.user.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponse getProfile(User user);

    UserResponse updateProfile(User user, UserUpdateRequest request);

    void softDeleteProfile(User user);

    List<UserResponse> listAllUsers();

    void toggleStatus(UUID id);

    void adminSoftDelete(UUID id);
}

