package com.habitplay.session.controller;

import com.habitplay.session.dto.response.HabitProgressResponse;
import com.habitplay.session.service.HabitProgressService;
import com.habitplay.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class HabitProgressController {

    private final HabitProgressService service;

    @PostMapping("/{sessionId}/{habitId}/increment")
    public HabitProgressResponse increment(
            @PathVariable UUID sessionId,
            @PathVariable UUID habitId
    ) {
        UUID userId = SecurityUtils.getCurrentUser().getId();
        return service.incrementProgress(habitId, sessionId, userId);
    }

    @PostMapping("/{sessionId}/{habitId}/complete")
    public HabitProgressResponse complete(
            @PathVariable UUID sessionId,
            @PathVariable UUID habitId
    ) {
        return service.markAsCompleted(sessionId, habitId);
    }

    @GetMapping("/{sessionId}")
    public List<HabitProgressResponse> listBySession(@PathVariable UUID sessionId) {
        return service.listBySession(sessionId);
    }

    @GetMapping("/{sessionId}/{habitId}")
    public HabitProgressResponse getProgress(
            @PathVariable UUID sessionId,
            @PathVariable UUID habitId
    ) {
        return service.findBySessionAndHabit(sessionId, habitId);
    }
}
