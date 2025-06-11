package com.habitplay.monster.dto.response;

import com.habitplay.monster.model.Monster;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MonsterResponse {

    private UUID id;
    private String name;
    private UUID difficultyId;
    private String difficultyName;
    private int baseHealth;
    private int maxHealth;
    private String imageUrl;
    private boolean active;

    public static MonsterResponse from(Monster monster) {
        return MonsterResponse.builder()
                .id(monster.getId())
                .name(monster.getName())
                .difficultyId(monster.getDifficulty().getId())
                .difficultyName(monster.getDifficulty().getName())
                .baseHealth(monster.getDifficulty().getBaseHealth())
                .maxHealth(monster.getMaxHealth())
                .imageUrl(monster.getImageUrl())
                .active(monster.getActive())
                .build();
    }
}
