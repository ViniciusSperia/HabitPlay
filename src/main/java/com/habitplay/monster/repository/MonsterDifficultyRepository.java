package com.habitplay.monster.repository;

import com.habitplay.monster.model.MonsterDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MonsterDifficultyRepository extends JpaRepository<MonsterDifficulty, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
