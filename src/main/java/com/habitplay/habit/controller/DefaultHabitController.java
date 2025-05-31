package com.habitplay.habit.controller;

import com.habitplay.habit.dto.response.DefaultHabitResponse;
import com.habitplay.habit.service.DefaultHabitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/public/default-habits")
@RequiredArgsConstructor
public class DefaultHabitController {

    private final DefaultHabitService defaultHabitService;

    @GetMapping
    public List<DefaultHabitResponse> getAllDefaultHabits() {
        log.info("Fetching all default habits");
        return defaultHabitService.getActiveDefaults();
    }
}
