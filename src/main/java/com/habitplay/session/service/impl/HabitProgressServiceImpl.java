package com.habitplay.session.service.impl;

import com.habitplay.config.exception.BusinessException;
import com.habitplay.config.exception.NotFoundException;
import com.habitplay.habit.model.Habit;
import com.habitplay.habit.repository.HabitRepository;
import com.habitplay.session.dto.response.HabitProgressResponse;
import com.habitplay.session.model.GameSession;
import com.habitplay.session.model.HabitProgress;
import com.habitplay.session.repository.GameSessionRepository;
import com.habitplay.session.repository.HabitProgressRepository;
import com.habitplay.session.service.HabitProgressService;
import com.habitplay.user.model.User;
import com.habitplay.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    @Transactional
    public HabitProgressResponse incrementProgress(UUID habitId, UUID sessionId, UUID userId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new NotFoundException("Habit not found"));

        GameSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        HabitProgress progress = progressRepository
                .findByHabitAndSessionAndUser(habitId, sessionId, userId)
                .orElseGet(() -> createProgressEntry(habit, session, user));

        if (progress.isCompleted()) {
            throw new BusinessException("Habit already completed");
        }

        progress.setProgress(progress.getProgress() + 1);

        if (progress.getProgress() >= habit.getTarget()) {
            progress.setCompleted(true);
            progress.setCompletionDate(LocalDateTime.now());
        }

        return HabitProgressResponse.from(progressRepository.save(progress));
    }

    @Override
    @Transactional
    public HabitProgressResponse markAsCompleted(UUID sessionId, UUID habitId) {
        HabitProgress progress = getProgressOrThrow(sessionId, habitId);
        if (!progress.isCompleted()) {
            progress.setCompleted(true);
            progress.setCompletionDate(LocalDateTime.now());

            GameSession session = progress.getGameSession();
            int damage = progress.getHabit().getDifficulty().getDamageValue();
            applyDamageToMonster(session, damage);
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

    private HabitProgress createProgressEntry(Habit habit, GameSession session, User user) {
        return progressRepository.save(HabitProgress.builder()
                .habit(habit)
                .user(user)
                .gameSession(session)
                .progress(0)
                .completed(false)
                .build());
    }

    private HabitProgress getProgressOrThrow(UUID sessionId, UUID habitId) {
        return progressRepository.findBySessionIdAndHabitId(sessionId, habitId)
                .orElseThrow(() -> new NotFoundException("Habit progress not found."));
    }

    private GameSession findSessionOrThrow(UUID sessionId) {
        return sessionRepository.findByIdAndActiveTrue(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found."));
    }

    private void applyDamageToMonster(GameSession session, int damage) {
        int newHealth = Math.max(0, session.getCurrentMonsterHealth() - damage);
        session.setCurrentMonsterHealth(newHealth);
        session.setUpdatedAt(LocalDateTime.now());

        if (newHealth == 0 && !session.isCompleted()) {
            session.setCompleted(true);
            session.setCompletionDate(LocalDateTime.now());
        }
    }
}
