package com.habitplay.session.service.impl;

import com.habitplay.config.exception.NotFoundException;
import com.habitplay.habit.model.DefaultHabit;
import com.habitplay.habit.model.Habit;
import com.habitplay.habit.repository.DefaultHabitRepository;
import com.habitplay.habit.repository.HabitRepository;
import com.habitplay.monster.model.Monster;
import com.habitplay.monster.repository.MonsterRepository;
import com.habitplay.session.dto.request.GameSessionRequest;
import com.habitplay.session.dto.request.GameSessionUpdateRequest;
import com.habitplay.session.dto.response.GameSessionResponse;
import com.habitplay.session.dto.response.GameSessionSummaryResponse;
import com.habitplay.session.model.GameSession;
import com.habitplay.session.model.SessionType;
import com.habitplay.session.repository.GameSessionRepository;
import com.habitplay.session.service.GameSessionService;
import com.habitplay.user.model.Role;
import com.habitplay.user.model.User;
import com.habitplay.user.repository.UserRepository;
import com.habitplay.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final MonsterRepository monsterRepository;

    @Override
    @Transactional
    public GameSessionResponse create(GameSessionRequest request) {
        validateRequest(request);
        User currentUser = SecurityUtils.getCurrentUser();

        if (request.getType() == SessionType.TEAM && currentUser.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Only admins can create TEAM sessions.");
        }

        boolean alreadyInActiveSession = sessionRepository
                .existsByUsersContainingAndTypeAndActiveTrue(currentUser, request.getType());

        if (alreadyInActiveSession) {
            throw new IllegalArgumentException("You already have an active " + request.getType() + " session.");
        }

        List<User> users = request.getType() == SessionType.TEAM
                ? loadUsers(request.getUserIds())
                : List.of(currentUser);

        validateUserSessionConflict(users);

        List<Habit> habitsFromDefaults = new ArrayList<>();
        if (request.getType() == SessionType.SOLO) {
            List<DefaultHabit> defaultHabits = defaultHabitRepository.findAllByActiveTrue();

            habitsFromDefaults = defaultHabits.stream()
                    .map(dh -> Habit.builder()
                            .name(dh.getName())
                            .description(dh.getDescription())
                            .difficulty(dh.getDifficulty())
                            .target(dh.getTarget())
                            .currentProgress(0)
                            .completed(false)
                            .active(true)
                            .user(currentUser)
                            .build())
                    .toList();

            habitRepository.saveAll(habitsFromDefaults);
            log.info("Cloned {} default habits for SOLO session", habitsFromDefaults.size());
        }

        List<Habit> extraHabits = new ArrayList<>();
        if (request.getType() == SessionType.TEAM && request.getHabitIds() != null && !request.getHabitIds().isEmpty()) {
            extraHabits = loadHabits(request.getHabitIds());
        }

        List<Habit> allHabits = new ArrayList<>(habitsFromDefaults);
        allHabits.addAll(extraHabits);

        Monster monster = monsterRepository.findById(request.getMonsterId())
                .orElseThrow(() -> new NotFoundException("Monster not found: " + request.getMonsterId()));

        GameSession session = GameSession.builder()
                .name(request.getName())
                .type(request.getType())
                .duration(request.getDuration())
                .startDate(request.getStartDate())
                .endDate(request.getStartDate().plusDays(request.getDuration().getDays()))
                .users(users)
                .habits(allHabits)
                .monster(monster)
                .currentMonsterHealth(monster.getMaxHealth())
                .active(true)
                .createdBy(currentUser)
                .build();

        log.info("User '{}' created a {} session with {} users and {} habits. Monster: '{}'",
                currentUser.getEmail(), request.getType(), users.size(), allHabits.size(), monster.getName());

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
    @Transactional
    public void deactivate(UUID sessionId) {
        GameSession session = findActiveSession(sessionId);
        session.setActive(false);
        session.setUpdatedAt(LocalDateTime.now());

        log.info("Session {} was deactivated by {}", sessionId, SecurityUtils.getCurrentUser().getEmail());
        sessionRepository.save(session);
    }

    @Override
    public GameSessionSummaryResponse findSummaryById(UUID sessionId) {
        GameSession session = sessionRepository.findByIdWithMonster(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found: " + sessionId));
        return GameSessionSummaryResponse.from(session);
    }

    @Override
    @Transactional
    public void reduceMonsterHealth(UUID sessionId, int amount) {
        GameSession session = findActiveSession(sessionId);
        int updatedHealth = Math.max(0, session.getCurrentMonsterHealth() - amount);
        session.setCurrentMonsterHealth(updatedHealth);
        session.setUpdatedAt(LocalDateTime.now());

        if (updatedHealth == 0 && !session.isCompleted()) {
            session.setCompleted(true);
            session.setCompletionDate(LocalDateTime.now());
            session.setActive(false);
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
        session.setCompleted(true);
        session.setCompletionDate(LocalDateTime.now());
        session.setActive(false);

        log.info("Session {} manually marked as completed", sessionId);
        sessionRepository.save(session);
    }

    @Override
    @Transactional
    public void checkAndCloseExpiredSessions() {
        List<GameSession> sessions = sessionRepository.findAllByActiveTrue();
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
                sessionRepository.save(session);

                log.info("Session {} automatically closed ({})",
                        session.getId(),
                        expiredByMonster ? "monster defeated" : "expired by date");
                closedCount++;
            }
        }

        log.info("Checked {} active sessions. {} were closed automatically.", sessions.size(), closedCount);
    }

    // ===================== HELPERS =====================

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
        if (request.getStartDate() == null || request.getStartDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start date must not be null or in the past.");
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

            if (request.getHabitIds() != null && !request.getHabitIds().isEmpty()) {
                long uniqueIdCount = request.getHabitIds().stream().distinct().count();
                if (uniqueIdCount < request.getHabitIds().size()) {
                    throw new IllegalArgumentException("Duplicated habit IDs are not allowed.");
                }

                List<Habit> habits = habitRepository.findAllById(request.getHabitIds());
                long uniqueNameCount = habits.stream()
                        .map(Habit::getName)
                        .map(String::toLowerCase)
                        .distinct()
                        .count();

                if (uniqueNameCount < habits.size()) {
                    throw new IllegalArgumentException("Duplicated habit names are not allowed.");
                }
            }
        }
    }

    private void validateUserSessionConflict(List<User> users) {
        for (User user : users) {
            if (user.getRole() == Role.ADMIN) {
                boolean exists = sessionRepository.existsByUsersContainingAndActiveTrue(user);
                if (exists) {
                    throw new IllegalArgumentException("Admin already has an active session.");
                }
            }
        }
    }
}
