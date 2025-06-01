package com.habitplay.session.service;

import com.habitplay.session.dto.response.HabitProgressResponse;

import java.util.List;
import java.util.UUID;

public interface HabitProgressService {

    HabitProgressResponse incrementProgress(UUID sessionId, UUID habitId);

    HabitProgressResponse markAsCompleted(UUID sessionId, UUID habitId);

    List<HabitProgressResponse> listBySession(UUID sessionId);

    HabitProgressResponse findBySessionAndHabit(UUID sessionId, UUID habitId);
}
