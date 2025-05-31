package com.habitplay.session.service.impl;

import com.habitplay.config.exception.NotFoundException;
import com.habitplay.session.dto.response.HabitProgressResponse;
import com.habitplay.session.model.GameSession;
import com.habitplay.session.model.HabitProgress;
import com.habitplay.session.repository.HabitProgressRepository;
import com.habitplay.session.repository.GameSessionRepository;
import com.habitplay.session.service.HabitProgressService;
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

    @Override
    @Transactional
    public HabitProgressResponse incrementProgress(UUID sessionId, UUID habitId, int amount) {
        HabitProgress progress = getProgressOrThrow(sessionId, habitId);

        if (progress.isCompleted()) {
            throw new IllegalArgumentException("Habit already completed.");
        }

        int updated = progress.getCurrentProgress() + amount;
        progress.setCurrentProgress(updated);

        int target = progress.getHabit().getTarget();
        if (updated >= target) {
            progress.setCompleted(true);
            progress.setCompletionDate(LocalDateTime.now());

            // Reduce monster health
            GameSession session = progress.getSession();
            int newHealth = Math.max(0, session.getCurrentMonsterHealth() - 100); // TODO: ajustar lógica
            session.setCurrentMonsterHealth(newHealth);
            session.setUpdatedAt(LocalDateTime.now());

            if (newHealth == 0 && !session.isCompleted()) {
                session.setCompleted(true);
                session.setCompletionDate(LocalDateTime.now());
            }

            log.info("Habit {} completed in session {}", habitId, sessionId);
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

            GameSession session = progress.getSession();
            int newHealth = Math.max(0, session.getCurrentMonsterHealth() - 100);
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
