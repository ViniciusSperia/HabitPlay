package com.habitplay.monster.dto.request;

import com.habitplay.monster.model.MonsterDifficulty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonsterRequest {

    @NotBlank
    private String name;

    @NotNull
    private MonsterDifficulty difficulty;

    @Min(1)
    private Integer maxHealth;

    @Min(0)
    private int damagePerHabit;

    @NotBlank
    private String imageUrl;
}
