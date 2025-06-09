package com.habitplay.habit.dto.request;

import com.habitplay.habit.model.Difficulty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class HabitRequest {

    @NotBlank
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    private Difficulty difficulty;

    @NotNull
    @Min(1)
    private Integer target;

    @NotNull
    private UUID userId;
}
