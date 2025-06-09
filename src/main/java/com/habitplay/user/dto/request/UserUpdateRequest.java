package com.habitplay.user.dto.request;

import com.habitplay.user.model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

    @NotBlank
    private String fullName;

    private String profileImageUrl;

    private Role role;
}
