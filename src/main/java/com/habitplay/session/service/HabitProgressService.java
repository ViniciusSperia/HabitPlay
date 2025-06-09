package com.habitplay.session.service;

import com.habitplay.session.dto.response.HabitProgressResponse;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface HabitProgressService {

    @Transactional
    HabitProgressResponse incrementProgress(UUID habitId, UUID sessionId, UUID userId);

    HabitProgressResponse markAsCompleted(UUID sessionId, UUID habitId);

    List<HabitProgressResponse> listBySession(UUID sessionId);

    HabitProgressResponse findBySessionAndHabit(UUID sessionId, UUID habitId);
}
