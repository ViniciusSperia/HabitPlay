package com.habitplay.habit.dto.request;

import com.habitplay.habit.model.Difficulty;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    private Difficulty difficulty;

    @NotNull
    @Min(value = 1, message = "Target must be at least 1")
    private Integer target;
}
