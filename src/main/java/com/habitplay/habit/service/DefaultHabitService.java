package com.habitplay.habit.service;

import com.habitplay.habit.dto.response.DefaultHabitResponse;
import com.habitplay.habit.model.DefaultHabit;
import com.habitplay.habit.repository.DefaultHabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultHabitService {

    private final DefaultHabitRepository defaultHabitRepository;

    public List<DefaultHabitResponse> getActiveDefaults() {
        return defaultHabitRepository.findAllByActiveTrue().stream()
                .map(DefaultHabitResponse::from)
                .toList();
    }

}
