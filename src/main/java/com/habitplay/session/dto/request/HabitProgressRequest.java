package com.habitplay.session.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record HabitProgressRequest(

        @NotNull UUID sessionId,

        @NotNull UUID habitId,

        int currentProgress
) {}
