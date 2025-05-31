package com.habitplay.habit.dto.response;

import com.habitplay.habit.model.DefaultHabit;
import com.habitplay.habit.model.Habit;

import java.util.List;

public record AvailableHabitsResponse(
        List<HabitResponse> customHabits,
        List<DefaultHabitResponse> defaultHabits
) {
    public static AvailableHabitsResponse of(List<Habit> habits, List<DefaultHabit> defaultHabits) {
        return new AvailableHabitsResponse(
                habits.stream().map(HabitResponse::from).toList(),
                defaultHabits.stream().map(DefaultHabitResponse::from).toList()
        );
    }
}
