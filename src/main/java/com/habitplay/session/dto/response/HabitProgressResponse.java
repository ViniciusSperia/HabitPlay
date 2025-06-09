package com.habitplay.session.dto.response;

import com.habitplay.session.model.HabitProgress;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record HabitProgressResponse(
        UUID id,
        UUID sessionId,
        UUID habitId,
        int currentProgress,
        boolean completed,
        LocalDateTime completionDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static HabitProgressResponse from(HabitProgress progress) {
        return HabitProgressResponse.builder()
                .id(progress.getId())
                .sessionId(progress.getGameSession().getId())
                .habitId(progress.getHabit().getId())
                .currentProgress(progress.getProgress())
                .completed(progress.isCompleted())
                .completionDate(progress.getCompletionDate())
                .createdAt(progress.getCreatedAt())
                .updatedAt(progress.getUpdatedAt())
                .build();
    }

}
