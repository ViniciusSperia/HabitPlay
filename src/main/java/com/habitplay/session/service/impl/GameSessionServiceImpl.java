package com.habitplay.session.service.impl;

import com.habitplay.habit.repository.DefaultHabitRepository;
import com.habitplay.config.exception.NotFoundException;
import com.habitplay.habit.model.Habit;
import com.habitplay.habit.repository.HabitRepository;
import com.habitplay.session.dto.request.GameSessionRequest;
import com.habitplay.session.dto.request.GameSessionUpdateRequest;
import com.habitplay.session.dto.response.GameSessionResponse;
import com.habitplay.session.model.GameSession;
import com.habitplay.session.model.SessionType;
import com.habitplay.session.repository.GameSessionRepository;
import com.habitplay.session.service.GameSessionService;
import com.habitplay.user.model.User;
import com.habitplay.user.repository.UserRepository;
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
public class GameSessionServiceImpl implements GameSessionService {

    private final GameSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final HabitRepository habitRepository;
    private final DefaultHabitRepository defaultHabitRepository;

    @Override
    @Transactional
    public GameSessionResponse create(GameSessionRequest request) {
        validateRequest(request);

        User currentUser = SecurityUtils.getCurrentUser();

        if (request.getType() == SessionType.TEAM && !currentUser.getRole().name().equals("ADMIN")) {
            throw new IllegalArgumentException("Only admins can create TEAM sessions.");
        }

        List<User> users;
        List<Habit> habits;

        if (request.getType() == SessionType.TEAM) {
            users = loadUsers(request.getUserIds());
            habits = loadHabits(request.getHabitIds());
        } else {
            users = List.of(currentUser);
            habits = List.of();
        }

        GameSession session = GameSession.builder()
                .name(request.getName())
                .type(request.getType())
                .duration(request.getDuration())
                .startDate(request.getStartDate())
                .endDate(request.getStartDate().plusDays(request.getDuration().getDays()))
                .users(users)
                .habits(habits)
                .active(true)
                .monsterHealth(1000)
                .currentMonsterHealth(1000)
                .createdBy(currentUser)
                .build();

        log.info("User {} is creating a new {} session", currentUser.getEmail(), request.getType());

        return GameSessionResponse.from(sessionRepository.save(session));
    }

    @Override
    @Transactional
    public GameSessionResponse update(UUID sessionId, GameSessionUpdateRequest request) {
        GameSession session = findActiveSession(sessionId);
        session.setName(request.getName());
        session.setStartDate(request.getStartDate());
        session.setEndDate(request.getStartDate().plusDays(session.getDuration().getDays()));
        session.setUpdatedAt(LocalDateTime.now());

        log.info("Session {} updated by user {}", sessionId, SecurityUtils.getCurrentUser().getEmail());
        return GameSessionResponse.from(sessionRepository.save(session));
    }

    @Override
    public GameSessionResponse findById(UUID sessionId) {
        GameSession session = findActiveSession(sessionId);
        return GameSessionResponse.from(session);
    }

    @Override
    public List<GameSessionResponse> listMySessions() {
        User user = SecurityUtils.getCurrentUser();
        return sessionRepository.findAllByUsersContainingAndActiveTrue(user).stream()
                .map(GameSessionResponse::from)
                .toList();
    }

    @Override
    public List<GameSessionResponse> listAllActive() {
        return sessionRepository.findAllByActiveTrue().stream()
                .map(GameSessionResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public void deactivate(UUID sessionId) {
        GameSession session = findActiveSession(sessionId);
        session.setActive(false);
        session.setUpdatedAt(LocalDateTime.now());

        log.info("Session {} was deactivated by {}", sessionId, SecurityUtils.getCurrentUser().getEmail());
        sessionRepository.save(session);
    }

    @Override
    @Transactional
    public void reduceMonsterHealth(UUID sessionId, int amount) {
        GameSession session = findActiveSession(sessionId);
        int current = session.getCurrentMonsterHealth();
        int updatedHealth = Math.max(0, current - amount);
        session.setCurrentMonsterHealth(updatedHealth);
        session.setUpdatedAt(LocalDateTime.now());

        if (updatedHealth == 0 && !session.isCompleted()) {
            session.setCompleted(true);
            session.setCompletionDate(LocalDateTime.now());
            log.info("Session {} marked as completed automatically (monster defeated)", sessionId);
        }

        log.info("Reduced monster health in session {} by {}. New health: {}", sessionId, amount, updatedHealth);
        sessionRepository.save(session);
    }


    @Override
    @Transactional
    public void markSessionAsCompleted(UUID sessionId) {
        GameSession session = findActiveSession(sessionId);
        session.setCurrentMonsterHealth(0);
        session.setUpdatedAt(LocalDateTime.now());

        log.info("Session {} marked as completed", sessionId);
        sessionRepository.save(session);
    }

    // ================== HELPERS ===================

    private GameSession findActiveSession(UUID id) {
        return sessionRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("Active session not found: " + id));
    }

    private List<User> loadUsers(List<UUID> userIds) {
        return userIds.stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("User not found: " + id)))
                .toList();
    }

    private List<Habit> loadHabits(List<UUID> habitIds) {
        return habitIds.stream()
                .map(id -> habitRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Habit not found: " + id)))
                .toList();
    }

    private void validateRequest(GameSessionRequest request) {
        if (request.getStartDate() == null) {
            throw new IllegalArgumentException("Start date must not be null.");
        }

        if (request.getStartDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start date must not be in the past.");
        }

        if (request.getDuration() == null) {
            throw new IllegalArgumentException("Duration must not be null.");
        }

        if (request.getType() == null) {
            throw new IllegalArgumentException("Session type must not be null.");
        }

        if (request.getType() == SessionType.TEAM) {
            if (request.getUserIds() == null || request.getUserIds().isEmpty()) {
                throw new IllegalArgumentException("TEAM sessions require at least one user.");
            }
            if (request.getHabitIds() == null || request.getHabitIds().isEmpty()) {
                throw new IllegalArgumentException("TEAM sessions require at least one habit.");
            }
        }
    }
}
