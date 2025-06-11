package com.habitplay.habit.service.impl;

import com.habitplay.config.exception.HabitNotFoundException;
import com.habitplay.config.exception.ResourceNotFoundException;
import com.habitplay.difficulty.model.Difficulty;
import com.habitplay.difficulty.repository.DifficultyRepository;
import com.habitplay.habit.dto.request.HabitRequest;
import com.habitplay.habit.dto.response.AvailableHabitsResponse;
import com.habitplay.habit.dto.response.HabitResponse;
import com.habitplay.habit.model.DefaultHabit;
import com.habitplay.habit.model.Habit;
import com.habitplay.habit.repository.DefaultHabitRepository;
import com.habitplay.habit.repository.HabitRepository;
import com.habitplay.habit.service.HabitService;
import com.habitplay.user.model.User;
import com.habitplay.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;
    private final DefaultHabitRepository defaultHabitRepository;
    private final DifficultyRepository difficultyRepository;

    @Override
    public HabitResponse create(HabitRequest request) {
        User user = SecurityUtils.getCurrentUser();

        Difficulty difficulty = difficultyRepository.findById(request.getDifficultyId())
                .orElseThrow(() -> new ResourceNotFoundException("Difficulty not found"));

        Habit habit = Habit.builder()
                .name(request.getName())
                .description(request.getDescription())
                .difficulty(difficulty)
                .target(request.getTarget())
                .damage(request.getDamage())
                .active(true)
                .user(user)
                .build();

        return HabitResponse.from(habitRepository.save(habit));
    }

    @Override
    @Transactional
    public HabitResponse update(UUID id, HabitRequest request) {
        Habit habit = findByIdAndValidateOwnership(id);

        Difficulty difficulty = difficultyRepository.findById(request.getDifficultyId())
                .orElseThrow(() -> new ResourceNotFoundException("Difficulty not found"));

        habit.setName(request.getName());
        habit.setDescription(request.getDescription());
        habit.setDifficulty(difficulty);
        habit.setDamage(request.getDamage());
        habit.setTarget(request.getTarget());
        habit.setUpdatedAt(LocalDateTime.now());

        return HabitResponse.from(habitRepository.save(habit));
    }

    @Override
    public List<HabitResponse> listMyHabits() {
        User user = SecurityUtils.getCurrentUser();
        return habitRepository.findByUserAndActiveTrue(user).stream()
                .map(HabitResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public void softDelete(UUID id) {
        Habit habit = findByIdAndValidateOwnership(id);
        habit.setActive(false);
        habit.setUpdatedAt(LocalDateTime.now());
        habitRepository.save(habit);
    }

    // ============ HELPERS ============

    private Habit findByIdAndValidateOwnership(UUID id) {
        User user = SecurityUtils.getCurrentUser();
        Habit habit = habitRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new HabitNotFoundException("Habit not found or inactive: " + id));

        if (!habit.getUser().getId().equals(user.getId())) {
            throw new HabitNotFoundException("Access denied for this habit");
        }

        return habit;
    }

    @Override
    public AvailableHabitsResponse getAvailableHabits() {
        User user = SecurityUtils.getCurrentUser();
        List<Habit> custom = habitRepository.findByUserAndActiveTrue(user);
        List<DefaultHabit> defaults = defaultHabitRepository.findAllByActiveTrue();
        return AvailableHabitsResponse.of(custom, defaults);
    }
}
