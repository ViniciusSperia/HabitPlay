package com.habitplay.difficulty.dto.response;

import com.habitplay.monster.model.Monster;
import com.habitplay.monster.model.MonsterDifficulty;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MonsterSummaryResponse {

    private UUID id;
    private String name;
    private MonsterDifficulty difficulty;
    private int maxHealth;
    private int damagePerHabit;
    private String imageUrl;

    public static MonsterSummaryResponse from(Monster monster) {
        return MonsterSummaryResponse.builder()
                .id(monster.getId())
                .name(monster.getName())
                .difficulty(monster.getDifficulty())
                .maxHealth(monster.getMaxHealth())
                .damagePerHabit(monster.getDamagePerHabit())
                .imageUrl(monster.getImageUrl())
                .build();
    }
}
