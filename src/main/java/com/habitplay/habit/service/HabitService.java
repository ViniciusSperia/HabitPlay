package com.habitplay.habit.service;


import com.habitplay.habit.dto.request.HabitRequest;
import com.habitplay.habit.dto.response.HabitResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface HabitService {
    HabitResponse create(HabitRequest request);
    List<HabitResponse> listMyHabits();
    HabitResponse update(UUID id, HabitRequest request);
    void softDelete(UUID id);
    HabitResponse incrementProgress(UUID id);
    HabitResponse markAsCompleted(UUID id);
}
