package com.habitplay.difficulty.dto.response;

import com.habitplay.difficulty.model.Difficulty;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DifficultyResponse {

    private UUID id;
    private String name;
    private boolean active;

    public static DifficultyResponse from(Difficulty difficulty) {
        return DifficultyResponse.builder()
                .id(difficulty.getId())
                .name(difficulty.getName())
                .active(difficulty.isActive())
                .build();
    }
}
