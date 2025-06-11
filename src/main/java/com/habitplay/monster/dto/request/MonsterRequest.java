package com.habitplay.monster.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MonsterRequest {

    @NotBlank
    private String name;

    @NotNull
    private UUID difficultyId;

    @Min(1)
    private Integer maxHealth;

    @NotBlank
    private String imageUrl;
}
