package com.habitplay.habit.service.impl;

import com.habitplay.config.exception.HabitNotFoundException;
import com.habitplay.habit.dto.request.HabitRequest;
import com.habitplay.habit.dto.response.HabitResponse;
import com.habitplay.habit.model.Habit;
import com.habitplay.habit.repository.HabitRepository;
import com.habitplay.habit.service.HabitService;
import com.habitplay.session.model.GameSession;
import com.habitplay.session.repository.GameSessionRepository;
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
    private final GameSessionRepository sessionRepository;

    public HabitResponse create(HabitRequest request) {
        User user = SecurityUtils.getCurrentUser();

        Habit habit = Habit.builder()
                .name(request.getName())
                .description(request.getDescription())
                .difficulty(request.getDifficulty())
                .target(request.getTarget())
                .currentProgress(0)
                .completed(false)
                .active(true)
                .user(user)
                .build();

        return HabitResponse.from(habitRepository.save(habit));
    }

    public List<HabitResponse> listMyHabits() {
        User user = SecurityUtils.getCurrentUser();
        return habitRepository.findByUserAndActiveTrue(user).stream()
                .map(HabitResponse::from)
                .toList();
    }

    @Transactional
    public HabitResponse update(UUID id, HabitRequest request) {
        Habit habit = findByIdAndValidateOwnership(id);

        habit.setName(request.getName());
        habit.setDescription(request.getDescription());
        habit.setDifficulty(request.getDifficulty());
        habit.setTarget(request.getTarget());
        habit.setUpdatedAt(LocalDateTime.now());

        return HabitResponse.from(habitRepository.save(habit));
    }

    @Transactional
    public void softDelete(UUID id) {
        Habit habit = findByIdAndValidateOwnership(id);
        habit.setActive(false);
        habit.setUpdatedAt(LocalDateTime.now());
        habitRepository.save(habit);
    }

    @Transactional
    public HabitResponse incrementProgress(UUID id) {
        Habit habit = findByIdAndValidateOwnership(id);

        if (habit.isCompleted()) {
            return HabitResponse.from(habit); // já completo
        }

        habit.setCurrentProgress(habit.getCurrentProgress() + 1);

        if (habit.getCurrentProgress() >= habit.getTarget()) {
            habit.setCompleted(true);
            habit.setCompletionDate(LocalDateTime.now());

            // Reduzir a vida do monstro nas sessões ativas que usam esse hábito
            reduceMonsterHealthFromSessions(habit);
        }

        habit.setUpdatedAt(LocalDateTime.now());
        return HabitResponse.from(habitRepository.save(habit));
    }

    @Transactional
    public HabitResponse markAsCompleted(UUID id) {
        Habit habit = findByIdAndValidateOwnership(id);

        if (!habit.isCompleted()) {
            habit.setCompleted(true);
            habit.setCompletionDate(LocalDateTime.now());

            // Reduzir vida do monstro
            reduceMonsterHealthFromSessions(habit);
        }

        habit.setUpdatedAt(LocalDateTime.now());
        return HabitResponse.from(habitRepository.save(habit));
    }

    private void reduceMonsterHealthFromSessions(Habit habit) {
        List<GameSession> sessions = sessionRepository.findByHabitsContainingAndActiveTrue(habit);

        int damage = switch (habit.getDifficulty()) {
            case EASY -> 50;
            case MEDIUM -> 100;
            case HARD -> 150;
        };

        for (GameSession session : sessions) {
            session.setCurrentMonsterHealth(Math.max(0, session.getCurrentMonsterHealth() - damage));
            session.setUpdatedAt(LocalDateTime.now());

            log.info("Habit '{}' completed. Monster health reduced by {} in session '{}'", habit.getName(), damage, session.getName());
            sessionRepository.save(session);
        }
    }

    private Habit findByIdAndValidateOwnership(UUID id) {
        User user = SecurityUtils.getCurrentUser();
        Habit habit = habitRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new HabitNotFoundException("Habit not found or inactive: " + id));

        if (!habit.getUser().getId().equals(user.getId())) {
            throw new HabitNotFoundException("Access denied for this habit");
        }

        return habit;
    }
}
