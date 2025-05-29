package com.habitplay.habit.repository;

import com.habitplay.habit.model.Habit;
import com.habitplay.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HabitRepository extends JpaRepository<Habit, UUID> {
    List<Habit> findByUserAndActiveTrue(User user);
}
