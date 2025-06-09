package com.habitplay.habit.controller;

import com.habitplay.habit.dto.response.DefaultHabitResponse;
import com.habitplay.habit.service.DefaultHabitService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/public/default-habits")
@RequiredArgsConstructor
public class DefaultHabitController {

    private final DefaultHabitService defaultHabitService;

    @Operation(
            summary = "List active default habits",
            description = "Returns all active default habits available to any user, without authentication."
    )
    @GetMapping
    public ResponseEntity<List<DefaultHabitResponse>> getAllDefaultHabits() {
        log.info("Fetching all default habits");
        return ResponseEntity.ok(defaultHabitService.getActiveDefaults());
    }
}
