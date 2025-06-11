package com.habitplay.habit.dto.request;

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
    private UUID difficultyId;

    @NotNull
    private Integer damage;

    @NotNull
    @Min(1)
    private Integer target;

    @NotNull
    private UUID userId;
}
