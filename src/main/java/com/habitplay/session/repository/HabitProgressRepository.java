package com.habitplay.session.repository;

import com.habitplay.habit.model.Habit;
import com.habitplay.session.model.GameSession;
import com.habitplay.session.model.HabitProgress;
import com.habitplay.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HabitProgressRepository extends JpaRepository<HabitProgress, UUID> {

    List<HabitProgress> findAllByGameSession(GameSession gameSession);

    Optional<HabitProgress> findByGameSessionIdAndHabitId(UUID gameSessionId, UUID habitId);

    Optional<HabitProgress> findByHabitAndGameSessionAndUser(Habit habit, GameSession gameSession, User user);
}
