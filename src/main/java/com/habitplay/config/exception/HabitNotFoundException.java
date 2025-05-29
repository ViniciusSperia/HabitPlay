package com.habitplay.config.exception;

import java.util.UUID;

public class HabitNotFoundException extends RuntimeException {
    public HabitNotFoundException(UUID id) {
        super("Habit not found with ID: " + id);
    }
}