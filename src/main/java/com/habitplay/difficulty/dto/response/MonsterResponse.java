package com.habitplay.difficulty.dto.response;

import com.habitplay.monster.model.Monster;
import com.habitplay.monster.model.MonsterDifficulty;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MonsterResponse {

    private UUID id;
    private String name;
    private MonsterDifficulty difficulty;
    private int maxHealth;
    private int damagePerHabit;
    private String imageUrl;
    private boolean active;

    public static MonsterResponse from(Monster monster) {
        return MonsterResponse.builder()
                .id(monster.getId())
                .name(monster.getName())
                .difficulty(monster.getDifficulty())
                .maxHealth(monster.getMaxHealth())
                .damagePerHabit(monster.getDamagePerHabit())
                .imageUrl(monster.getImageUrl())
                .active(monster.isActive())
                .build();
    }
}
