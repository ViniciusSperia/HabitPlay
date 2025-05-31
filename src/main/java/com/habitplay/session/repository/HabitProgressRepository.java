package com.habitplay.session.repository;

import com.habitplay.session.model.GameSession;
import com.habitplay.session.model.HabitProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HabitProgressRepository extends JpaRepository<HabitProgress, UUID> {

    List<HabitProgress> findAllBySession(GameSession session);

    Optional<HabitProgress> findBySessionIdAndHabitId(UUID sessionId, UUID habitId);
}
