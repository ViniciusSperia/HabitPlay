package com.habitplay.habit.model;

public enum Difficulty {
    EASY,
    MEDIUM,
    HARD;

    public int getDamageValue() {
        return switch (this) {
            case EASY -> 30;
            case MEDIUM -> 50;
            case HARD -> 70;
        };
    }
}
