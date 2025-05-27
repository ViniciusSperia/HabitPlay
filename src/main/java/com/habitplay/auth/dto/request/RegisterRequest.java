package com.habitplay.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\\\"\\\\|,.<>\\/?]).{8,32}$",
            message = "A senha deve conter entre 8 e 32 caracteres, com pelo menos uma letra maiúscula, uma minúscula, um número e um símbolo (incluindo , ou .)"
    )
    private String password;

    private String profileImageUrl;
}