package com.habitplay.session.dto.response;

import com.habitplay.monster.dto.response.MonsterSummaryResponse;
import com.habitplay.session.model.GameSession;
import com.habitplay.session.model.SessionDuration;
import com.habitplay.session.model.SessionType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class GameSessionResponse {
    private UUID id;
    private String name;
    private SessionType type;
    private SessionDuration duration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
    private int monsterHealth;
    private int currentMonsterHealth;
    private MonsterSummaryResponse monster;
    private boolean completed;
    private LocalDateTime completionDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GameSessionResponse from(GameSession session) {
        return GameSessionResponse.builder()
                .id(session.getId())
                .name(session.getName())
                .type(session.getType())
                .duration(session.getDuration())
                .startDate(session.getStartDate())
                .endDate(session.getEndDate())
                .active(session.isActive())
                .monsterHealth(session.getMonster().getMaxHealth())
                .currentMonsterHealth(session.getCurrentMonsterHealth())
                .monster(MonsterSummaryResponse.from(session.getMonster()))
                .completed(session.isCompleted())
                .completionDate(session.getCompletionDate())
                .createdAt(session.getCreatedAt())
                .updatedAt(session.getUpdatedAt())
                .build();
    }
}

