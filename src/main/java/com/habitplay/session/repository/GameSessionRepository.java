package com.habitplay.session.repository;

import com.habitplay.habit.model.Habit;
import com.habitplay.session.model.GameSession;
import com.habitplay.session.model.SessionType;
import com.habitplay.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameSessionRepository extends JpaRepository<GameSession, UUID> {

    Optional<GameSession> findByIdAndActiveTrue(UUID id);

    List<GameSession> findAllByUsersContainingAndActiveTrue(User user);

    List<GameSession> findAllByActiveTrue();

    List<GameSession> findByHabitsContainingAndActiveTrue(Habit habit);

    Boolean existsByUsersContainingAndActiveTrue(User user);
    boolean existsByUsersContainingAndTypeAndActiveTrue(User user, SessionType type);

    List<GameSession> findByHabitsContaining(Habit habit);

    @Query("SELECT s FROM GameSession s JOIN FETCH s.monster WHERE s.id = :id")
    Optional<GameSession> findByIdWithMonster(@Param("id") UUID id);

}
