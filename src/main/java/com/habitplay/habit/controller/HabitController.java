package com.habitplay.habit.controller;

import com.habitplay.habit.dto.request.HabitRequest;
import com.habitplay.habit.dto.response.HabitResponse;
import com.habitplay.habit.service.HabitService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/habits")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @Operation(
            summary = "Create a new habit",
            description = "Allows the authenticated user to create a new habit with name, description, difficulty, and target."
    )
    @PostMapping
    public ResponseEntity<HabitResponse> createHabit(@RequestBody @Valid HabitRequest request) {
        return ResponseEntity.ok(habitService.create(request));
    }

    @Operation(
            summary = "List active habits",
            description = "Returns all active habits created by the authenticated user."
    )
    @GetMapping
    public ResponseEntity<List<HabitResponse>> listHabits() {
        return ResponseEntity.ok(habitService.listByUser());
    }

    @Operation(
            summary = "Update an existing habit",
            description = "Updates the name, description, difficulty, and target of an existing habit owned by the authenticated user."
    )
    @PutMapping("/{id}")
    public ResponseEntity<HabitResponse> updateHabit(
            @PathVariable UUID id,
            @RequestBody @Valid HabitRequest request
    ) {
        return ResponseEntity.ok(habitService.update(id, request));
    }

    @Operation(
            summary = "Delete a habit (soft delete)",
            description = "Marks the specified habit as inactive (soft delete) for the authenticated user."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable UUID id) {
        habitService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/increment")
    public ResponseEntity<Void> incrementHabitProgress(@PathVariable UUID id) {
        habitService.incrementProgress(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeHabit(@PathVariable UUID id) {
        habitService.markAsCompleted(id);
        return ResponseEntity.noContent().build();
    }
}
