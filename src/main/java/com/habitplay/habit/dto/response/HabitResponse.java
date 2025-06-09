package com.habitplay.habit.dto.response;

import com.habitplay.habit.model.Difficulty;
import com.habitplay.habit.model.Habit;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Setter
@Getter
@Builder
public class HabitResponse {

    private UUID id;
    private String name;
    private String description;
    private Difficulty difficulty;
    private int target;
    private boolean active;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static HabitResponse from(Habit habit) {
        return HabitResponse.builder()
                .id(habit.getId())
                .name(habit.getName())
                .description(habit.getDescription())
                .difficulty(habit.getDifficulty())
                .target(habit.getTarget())
                .active(habit.isActive())
                .userId(habit.getUser().getId())
                .createdAt(habit.getCreatedAt())
                .updatedAt(habit.getUpdatedAt())
                .build();
    }
}
