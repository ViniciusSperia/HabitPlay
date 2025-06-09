package com.habitplay.difficulty.model;

public enum MonsterDifficulty {
    EASY,
    MEDIUM,
    HARD,
    BOSS;

    public int getDefaultMaxHealth() {
        return switch (this) {
            case EASY -> 500;
            case MEDIUM -> 1000;
            case HARD -> 2000;
            case BOSS -> 5000;
        };
    }
}
