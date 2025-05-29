package com.habitplay.habit.dto.response;

import com.habitplay.habit.model.Difficulty;
import com.habitplay.habit.model.Habit;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class HabitResponse {

    private UUID id;
    private String name;
    private String description;
    private Difficulty difficulty;
    private Integer target;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static HabitResponse from(Habit habit) {
        return HabitResponse.builder()
                .id(habit.getId())
                .name(habit.getName())
                .description(habit.getDescription())
                .difficulty(habit.getDifficulty())
                .target(habit.getTarget())
                .createdAt(habit.getCreatedAt())
                .updatedAt(habit.getUpdatedAt())
                .build();
    }
}
