package com.habitplay.session.repository;

import com.habitplay.habit.model.Habit;
import com.habitplay.session.model.GameSession;
import com.habitplay.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameSessionRepository extends JpaRepository<GameSession, UUID> {

    Optional<GameSession> findByIdAndActiveTrue(UUID id);

    List<GameSession> findAllByUsersContainingAndActiveTrue(User user);

    List<GameSession> findAllByActiveTrue();

    List<GameSession> findByHabitsContainingAndActiveTrue(Habit habit);
}
