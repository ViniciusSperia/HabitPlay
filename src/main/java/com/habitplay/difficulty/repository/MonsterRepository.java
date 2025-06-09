package com.habitplay.difficulty.repository;

import com.habitplay.monster.model.Monster;
import com.habitplay.monster.model.MonsterDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MonsterRepository extends JpaRepository<Monster, UUID> {

    List<Monster> findAllByDifficulty(MonsterDifficulty difficulty);
    List<Monster> findAllByActiveTrue();
    List<Monster> findAllByDifficultyAndActiveTrue(MonsterDifficulty difficulty);

}
