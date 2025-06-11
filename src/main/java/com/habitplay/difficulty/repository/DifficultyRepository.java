package com.habitplay.difficulty.repository;

import com.habitplay.difficulty.model.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DifficultyRepository extends JpaRepository<Difficulty, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
