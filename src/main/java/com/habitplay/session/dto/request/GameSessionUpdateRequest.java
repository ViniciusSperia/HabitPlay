package com.habitplay.session.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GameSessionUpdateRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDateTime startDate;
}
