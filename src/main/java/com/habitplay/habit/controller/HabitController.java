package com.habitplay.habit.controller;

import com.habitplay.habit.dto.request.HabitRequest;
import com.habitplay.habit.dto.response.HabitResponse;
import com.habitplay.habit.service.HabitService;
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

    @PostMapping
    public ResponseEntity<HabitResponse> createHabit(@RequestBody @Valid HabitRequest request) {
        return ResponseEntity.ok(habitService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<HabitResponse>> listHabits() {
        return ResponseEntity.ok(habitService.listByUser());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitResponse> updateHabit(
            @PathVariable UUID id,
            @RequestBody @Valid HabitRequest request
    ) {
        return ResponseEntity.ok(habitService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable UUID id) {
        habitService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}
