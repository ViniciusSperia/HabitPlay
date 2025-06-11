package com.habitplay.difficulty.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DifficultyRequest {

    @NotBlank
    private String name;
}
