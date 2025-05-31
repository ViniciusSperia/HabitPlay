package com.habitplay.habit.dto.response;

import com.habitplay.habit.model.DefaultHabit;

import java.util.UUID;

public record DefaultHabitResponse(
        UUID id,
        String name,
        String description,
        String difficulty,
        int target
) {
    public static DefaultHabitResponse from(DefaultHabit habit) {
        return new DefaultHabitResponse(
                habit.getId(),
                habit.getName(),
                habit.getDescription(),
                habit.getDifficulty().name(),
                habit.getTarget()
        );
    }
}
