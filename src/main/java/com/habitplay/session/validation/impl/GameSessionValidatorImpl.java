package com.habitplay.session.validator;

import com.habitplay.config.exception.NotFoundException;
import com.habitplay.habit.model.Habit;
import com.habitplay.habit.repository.HabitRepository;
import com.habitplay.session.dto.request.GameSessionRequest;
import com.habitplay.session.model.SessionDuration;
import com.habitplay.session.model.SessionType;
import com.habitplay.session.repository.GameSessionRepository;
import com.habitplay.user.model.Role;
import com.habitplay.user.model.User;
import com.habitplay.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class GameSessionValidatorImpl {

    private final GameSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final HabitRepository habitRepository;

    public void validateRequest(GameSessionRequest request, User currentUser) {
        validateStartDate(request.getStartDate());
        validateDuration(request.getDuration());
        validateType(request.getType());

        if (request.getType() == SessionType.TEAM) {
            validateAdminRights(currentUser);
            validateUsersExist(request.getUserIds());
        } else {
            validateSoloSessionUniqueness(currentUser, request.getType());
        }

        validateHabits(request.getHabitIds());
    }

    private void validateStartDate(LocalDateTime startDate) {
        if (startDate == null || startDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start date must not be null or in the past.");
        }
    }

    private void validateDuration(SessionDuration duration) {
        if (duration == null) {
            throw new IllegalArgumentException("Duration must not be null.");
        }
    }

    private void validateType(SessionType type) {
        if (type == null) {
            throw new IllegalArgumentException("Session type must not be null.");
        }
    }

    private void validateAdminRights(User user) {
        if (!"ADMIN".equalsIgnoreCase(user.getRole().getName())) {
            throw new IllegalArgumentException("Only admins can create TEAM sessions.");
        }
    }


    private void validateUsersExist(List<UUID> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            throw new IllegalArgumentException("TEAM sessions require at least one user.");
        }

        for (UUID id : userIds) {
            if (!userRepository.existsById(id)) {
                throw new NotFoundException("User not found: " + id);
            }
        }
    }

    private void validateSoloSessionUniqueness(User user, SessionType type) {
        boolean exists = sessionRepository.existsByUsersContainingAndTypeAndActiveTrue(user, type);
        if (exists) {
            throw new IllegalArgumentException("You already have an active " + type + " session.");
        }
    }

    private void validateHabits(List<UUID> habitIds) {
        if (habitIds == null || habitIds.isEmpty()) return;

        if (new HashSet<>(habitIds).size() < habitIds.size()) {
            throw new IllegalArgumentException("Duplicated habit IDs are not allowed.");
        }

        List<Habit> habits = habitRepository.findAllById(habitIds);

        if (habits.size() < habitIds.size()) {
            throw new NotFoundException("Some habit IDs do not exist.");
        }

        long uniqueNames = habits.stream()
                .map(h -> h.getName().toLowerCase())
                .distinct()
                .count();

        if (uniqueNames < habits.size()) {
            throw new IllegalArgumentException("Duplicated habit names are not allowed.");
        }
    }
}