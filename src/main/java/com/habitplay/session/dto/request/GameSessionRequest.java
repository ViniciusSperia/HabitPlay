package com.habitplay.session.dto.request;

import com.habitplay.session.model.SessionDuration;
import com.habitplay.session.model.SessionType;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GameSessionRequest {

    @NotBlank
    private String name;

    @NotNull
    private SessionType type;

    @NotNull
    private SessionDuration duration;

    @NotNull
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDateTime startDate;

    @NotEmpty
    private List<UUID> userIds;

    @NotEmpty
    private List<UUID> habitIds;
}
