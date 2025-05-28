package com.habitplay.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRefreshRequest {
    @NotBlank
    private String token;
}