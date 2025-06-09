package com.habitplay.session.dto.response;

import com.habitplay.session.model.GameSession;
import com.habitplay.session.model.SessionDuration;
import com.habitplay.session.model.SessionType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class    GameSessionSummaryResponse {
    private UUID id;
    private String name;
    private SessionType type;
    private SessionDuration duration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
    private boolean completed;
    private int monsterHealth;
    private int currentMonsterHealth;
    private LocalDateTime completionDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GameSessionSummaryResponse from(GameSession session) {
        return GameSessionSummaryResponse.builder()
                .id(session.getId())
                .name(session.getName())
                .type(session.getType())
                .duration(session.getDuration())
                .startDate(session.getStartDate())
                .endDate(session.getEndDate())
                .active(session.isActive())
                .completed(session.isCompleted())
                .monsterHealth(session.getMonster().getMaxHealth())
                .currentMonsterHealth(session.getCurrentMonsterHealth())
                .completionDate(session.getCompletionDate()) // <== necessário
                .createdAt(session.getCreatedAt())
                .updatedAt(session.getUpdatedAt())
                .build();
    }
}

