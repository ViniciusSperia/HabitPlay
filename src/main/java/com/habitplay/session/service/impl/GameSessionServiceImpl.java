package com.habitplay.session.service.impl;

import com.habitplay.config.exception.NotFoundException;
import com.habitplay.habit.model.Habit;
import com.habitplay.habit.repository.HabitRepository;
import com.habitplay.monster.model.Monster;
import com.habitplay.monster.repository.MonsterRepository;
import com.habitplay.session.dto.request.GameSessionRequest;
import com.habitplay.session.dto.request.GameSessionUpdateRequest;
import com.habitplay.session.dto.response.GameSessionResponse;
import com.habitplay.session.dto.response.GameSessionSummaryResponse;
import com.habitplay.session.model.GameSession;
import com.habitplay.session.repository.GameSessionRepository;
import com.habitplay.session.service.GameSessionService;
import com.habitplay.session.validator.GameSessionValidatorImpl;
import com.habitplay.user.model.User;
import com.habitplay.user.repository.UserRepository;
import com.habitplay.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameSessionServiceImpl implements GameSessionService {

    private final GameSessionRepository gameSessionRepository;
    private final UserRepository userRepository;
    private final HabitRepository habitRepository;
    private final MonsterRepository monsterRepository;
    private final GameSessionValidatorImpl validator;

    @Override
    @Transactional
    public GameSessionResponse create(GameSessionRequest request) {
        User currentUser = SecurityUtils.getCurrentUser();
        validator.validateRequest(request, currentUser);

        List<User> users = resolveUsersForSession(request, currentUser);
        List<Habit> habits = resolveHabitsForSession(request);
        Monster monster = monsterRepository.findById(request.getMonsterId())
                .orElseThrow(() -> new NotFoundException("Monster not found"));

        GameSession session = GameSession.builder()
                .name(request.getName())
                .type(request.getType())
                .duration(request.getDuration())
                .startDate(request.getStartDate())
                .endDate(request.getStartDate().plusDays(request.getDuration().getDays()))
                .users(users)
                .monster(monster)
                .currentMonsterHealth(monster.getMaxHealth())
                .active(true)
                .completed(false)
                .createdBy(currentUser)
                .build();

        return GameSessionResponse.from(gameSessionRepository.save(session));
    }

    @Override
    public GameSessionResponse findById(UUID id) {
        GameSession session = gameSessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Game session not found"));
        SecurityUtils.validateOwnership(session.getCreatedBy());
        return GameSessionResponse.from(session);
    }

    @Override
    @Transactional
    public GameSessionResponse update(UUID id, GameSessionUpdateRequest request) {
        GameSession session = gameSessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Game session not found"));
        SecurityUtils.validateOwnership(session.getCreatedBy());

        session.setName(request.getName());
        session.setStartDate(request.getStartDate());
        session.setEndDate(request.getStartDate().plusDays(session.getDuration().getDays()));
        session.setUpdatedAt(LocalDateTime.now());

        return GameSessionResponse.from(gameSessionRepository.save(session));
    }

    @Override
    public List<GameSessionSummaryResponse> listMySessions() {
        User currentUser = SecurityUtils.getCurrentUser();
        List<GameSession> sessions = gameSessionRepository.findByUsersContaining(currentUser);
        return sessions.stream()
                .map(GameSessionSummaryResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<GameSessionResponse> listAllActive() {
        return gameSessionRepository.findAllByActiveTrue().stream()
                .map(GameSessionResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public void deactivate(UUID id) {
        GameSession session = gameSessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session not found"));
        SecurityUtils.validateOwnership(session.getCreatedBy());

        session.setActive(false);
        session.setUpdatedAt(LocalDateTime.now());

        gameSessionRepository.save(session);
    }

    @Override
    @Transactional
    public void reduceMonsterHealth(UUID id, int amount) {
        GameSession session = gameSessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session not found"));

        int newHealth = Math.max(0, session.getCurrentMonsterHealth() - amount);
        session.setCurrentMonsterHealth(newHealth);
        session.setUpdatedAt(LocalDateTime.now());

        if (newHealth == 0 && !session.isCompleted()) {
            session.setCompleted(true);
            session.setCompletionDate(LocalDateTime.now());
            session.setActive(false);
        }

        gameSessionRepository.save(session);
    }

    @Override
    @Transactional
    public void markSessionAsCompleted(UUID id) {
        GameSession session = gameSessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session not found"));
        SecurityUtils.validateOwnership(session.getCreatedBy());

        session.setCurrentMonsterHealth(0);
        session.setCompleted(true);
        session.setActive(false);
        session.setCompletionDate(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());

        gameSessionRepository.save(session);
    }

    @Override
    public GameSessionSummaryResponse findSummaryById(UUID id) {
        GameSession session = gameSessionRepository.findByIdWithMonster(id)
                .orElseThrow(() -> new NotFoundException("Session not found: " + id));
        return GameSessionSummaryResponse.from(session);
    }

    @Override
    @Transactional
    public void checkAndCloseExpiredSessions() {
        List<GameSession> sessions = gameSessionRepository.findAllByActiveTrue();
        LocalDateTime now = LocalDateTime.now();
        int closedCount = 0;

        for (GameSession session : sessions) {
            boolean expiredByDate = session.getEndDate() != null && now.isAfter(session.getEndDate());
            boolean expiredByMonster = session.getCurrentMonsterHealth() <= 0;

            if ((expiredByDate || expiredByMonster) && !session.isCompleted()) {
                session.setActive(false);
                session.setCompleted(true);
                session.setCompletionDate(now);
                session.setUpdatedAt(now);
                gameSessionRepository.save(session);
                closedCount++;
            }
        }

        log.info("Checked {} active sessions. {} were closed automatically.", sessions.size(), closedCount);
    }

    // ===================== Helpers =====================

    private List<User> resolveUsersForSession(GameSessionRequest request, User currentUser) {
        if (request.getType().name().equalsIgnoreCase("TEAM")) {
            return userRepository.findAllById(request.getUserIds());
        } else {
            return List.of(currentUser);
        }
    }

    private List<Habit> resolveHabitsForSession(GameSessionRequest request) {
        if (request.getHabitIds() != null && !request.getHabitIds().isEmpty()) {
            return habitRepository.findAllById(request.getHabitIds());
        }
        return List.of(); // Para SOLO, hábitos padrão são tratados separadamente
    }
}
