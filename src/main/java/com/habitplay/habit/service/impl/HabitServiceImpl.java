package com.habitplay.habit.service.impl;

import com.habitplay.config.exception.HabitNotFoundException;
import com.habitplay.habit.dto.request.HabitRequest;
import com.habitplay.habit.dto.response.HabitResponse;
import com.habitplay.habit.model.Habit;
import com.habitplay.habit.repository.HabitRepository;
import com.habitplay.habit.service.HabitService;
import com.habitplay.user.model.User;
import com.habitplay.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;

    @Override
    @Transactional
    public HabitResponse create(HabitRequest request) {
        User currentUser = SecurityUtils.getCurrentUser();
        Habit habit = Habit.builder()
                .name(request.getName())
                .description(request.getDescription())
                .difficulty(request.getDifficulty())
                .target(request.getTarget())
                .user(currentUser)
                .active(true)
                .build();

        return HabitResponse.from(habitRepository.save(habit));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitResponse> listByUser() {
        User currentUser = SecurityUtils.getCurrentUser();
        return habitRepository.findByUserAndActiveTrue(currentUser)
                .stream()
                .map(HabitResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public HabitResponse update(UUID id, HabitRequest request) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Habit not found"));

        SecurityUtils.validateOwnership(habit.getUser());

        habit.setName(request.getName());
        habit.setDescription(request.getDescription());
        habit.setDifficulty(request.getDifficulty());
        habit.setTarget(request.getTarget());

        return HabitResponse.from(habitRepository.save(habit));
    }

    @Override
    @Transactional
    public void softDelete(UUID id) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new HabitNotFoundException(id));

        SecurityUtils.validateOwnership(habit.getUser());

        habit.setActive(false);
        habitRepository.save(habit);
    }

    @Override
    @Transactional
    public void incrementProgress(UUID id) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Habit not found"));

        SecurityUtils.validateOwnership(habit.getUser());

        if (habit.isCompleted()) return;

        int newProgress = habit.getCurrentProgress() + 1;
        habit.setCurrentProgress(newProgress);

        if (newProgress >= habit.getTarget()) {
            habit.setCompleted(true);
            habit.setCompletionDate(LocalDateTime.now());
        }

        habitRepository.save(habit);
    }

    @Override
    @Transactional
    public void markAsCompleted(UUID id) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Habit not found"));

        SecurityUtils.validateOwnership(habit.getUser());

        if (!habit.isCompleted()) {
            habit.setCompleted(true);
            habit.setCompletionDate(LocalDateTime.now());
            habitRepository.save(habit);
        }
    }
}
