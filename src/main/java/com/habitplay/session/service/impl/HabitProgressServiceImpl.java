package com.habitplay.session.service.impl;

import com.habitplay.config.exception.NotFoundException;
import com.habitplay.habit.model.Habit;
import com.habitplay.habit.repository.HabitRepository;
import com.habitplay.session.dto.response.HabitProgressResponse;
import com.habitplay.session.model.GameSession;
import com.habitplay.session.model.HabitProgress;
import com.habitplay.session.repository.HabitProgressRepository;
import com.habitplay.session.repository.GameSessionRepository;
import com.habitplay.session.service.HabitProgressService;
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
public class HabitProgressServiceImpl implements HabitProgressService {

    private final HabitProgressRepository progressRepository;
    private final GameSessionRepository sessionRepository;
    private final HabitRepository habitRepository;
    private final GameSessionServiceImpl gameSessionService;

    @Override
    @Transactional
    public HabitProgressResponse incrementProgress(UUID sessionId, UUID habitId) {
        User user = SecurityUtils.getCurrentUser();

        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new NotFoundException("Habit not found: " + habitId));

        GameSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found: " + sessionId));

        if (habit.isCompleted()) {
            throw new IllegalStateException("Habit is already completed.");
        }

        if (!session.isActive() || session.isCompleted()) {
            throw new IllegalStateException("Cannot update habit in a completed or inactive session.");
        }

        int updatedProgress = habit.getCurrentProgress() + 1;
        habit.setCurrentProgress(updatedProgress);
        habit.setUpdatedAt(LocalDateTime.now());

        log.info("User {} incremented progress of habit '{}' in session {} to {}",
                user.getEmail(), habit.getName(), sessionId, updatedProgress);

        if (updatedProgress >= habit.getTarget()) {
            habit.setCompleted(true);
            habit.setCompletionDate(LocalDateTime.now());

            int damage = habit.getDifficulty().getDamageValue();
            log.info("Habit '{}' completed. Applying {} damage to monster in session {}", habit.getName(), damage, sessionId);
            gameSessionService.reduceMonsterHealth(sessionId, damage);
        }

        habitRepository.save(habit);
        return HabitProgressResponse.from(getProgressOrThrow(sessionId, habitId));
    }

    @Override
    @Transactional
    public HabitProgressResponse markAsCompleted(UUID sessionId, UUID habitId) {
        HabitProgress progress = getProgressOrThrow(sessionId, habitId);
        if (!progress.isCompleted()) {
            progress.setCompleted(true);
            progress.setCompletionDate(LocalDateTime.now());

            GameSession session = progress.getSession();
            int damage = progress.getHabit().getDifficulty().getDamageValue();
            int newHealth = Math.max(0, session.getCurrentMonsterHealth() - damage);
            session.setCurrentMonsterHealth(newHealth);
            session.setUpdatedAt(LocalDateTime.now());

            if (newHealth == 0 && !session.isCompleted()) {
                session.setCompleted(true);
                session.setCompletionDate(LocalDateTime.now());
            }
        }
        return HabitProgressResponse.from(progressRepository.save(progress));
    }

    @Override
    public List<HabitProgressResponse> listBySession(UUID sessionId) {
        GameSession session = findSessionOrThrow(sessionId);
        return progressRepository.findAllBySession(session).stream()
                .map(HabitProgressResponse::from)
                .toList();
    }

    @Override
    public HabitProgressResponse findBySessionAndHabit(UUID sessionId, UUID habitId) {
        return HabitProgressResponse.from(getProgressOrThrow(sessionId, habitId));
    }

    // ===================== HELPERS =====================

    private HabitProgress getProgressOrThrow(UUID sessionId, UUID habitId) {
        return progressRepository.findBySessionIdAndHabitId(sessionId, habitId)
                .orElseThrow(() -> new NotFoundException("Habit progress not found."));
    }

    private GameSession findSessionOrThrow(UUID sessionId) {
        return sessionRepository.findByIdAndActiveTrue(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found."));
    }
}
