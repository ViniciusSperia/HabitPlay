package com.habitplay.monster.dto.response;

import com.habitplay.monster.model.Monster;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MonsterSummaryResponse {

    private UUID id;
    private String name;
    private UUID difficultyId;
    private String difficultyName;
    private int maxHealth;
    private String imageUrl;

    public static MonsterSummaryResponse from(Monster monster) {
        return MonsterSummaryResponse.builder()
                .id(monster.getId())
                .name(monster.getName())
                .difficultyId(monster.getDifficulty().getId())
                .difficultyName(monster.getDifficulty().getName())
                .maxHealth(monster.getMaxHealth())
                .imageUrl(monster.getImageUrl())
                .build();
    }
}
