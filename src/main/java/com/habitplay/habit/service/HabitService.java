package com.habitplay.habit.service;

import com.habitplay.habit.dto.request.HabitRequest;
import com.habitplay.habit.dto.response.AvailableHabitsResponse;
import com.habitplay.habit.dto.response.HabitResponse;

import java.util.List;
import java.util.UUID;

public interface HabitService {
    HabitResponse create(HabitRequest request);
    List<HabitResponse> listMyHabits();
    HabitResponse update(UUID id, HabitRequest request);
    void softDelete(UUID id);
    AvailableHabitsResponse getAvailableHabits();
}
