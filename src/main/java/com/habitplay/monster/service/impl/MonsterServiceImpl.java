package com.habitplay.monster.service.impl;

import com.habitplay.config.exception.NotFoundException;
import com.habitplay.monster.dto.request.MonsterRequest;
import com.habitplay.monster.dto.response.MonsterResponse;
import com.habitplay.monster.model.Monster;
import com.habitplay.monster.model.MonsterDifficulty;
import com.habitplay.monster.repository.MonsterDifficultyRepository;
import com.habitplay.monster.repository.MonsterRepository;
import com.habitplay.monster.service.MonsterService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j

@RequiredArgsConstructor
@Service
public class MonsterServiceImpl implements MonsterService {

    private final MonsterRepository monsterRepository;
    private final MonsterDifficultyRepository monsterDifficultyRepository;

    @Override
    @Transactional
    public MonsterResponse create(MonsterRequest request) {
        validateUniqueName(request.getName(), null);

        MonsterDifficulty difficulty = monsterDifficultyRepository.findById(request.getDifficultyId())
                .orElseThrow(() -> new NotFoundException("Monster difficulty not found: " + request.getDifficultyId()));

        int health = request.getMaxHealth() != null ? request.getMaxHealth() : difficulty.getBaseHealth();

        Monster monster = Monster.builder()
                .name(request.getName())
                .difficulty(difficulty)
                .maxHealth(health)
                .imageUrl(request.getImageUrl())
                .active(true)
                .build();

        Monster saved = monsterRepository.save(monster);
        log.info("Monster '{}' created with difficulty {}", saved.getName(), saved.getDifficulty().getName());
        return MonsterResponse.from(saved);
    }

    @Override
    @Transactional
    public MonsterResponse update(UUID id, MonsterRequest request) {
        Monster monster = monsterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Monster not found: " + id));

        validateUniqueName(request.getName(), id);

        MonsterDifficulty difficulty = monsterDifficultyRepository.findById(request.getDifficultyId())
                .orElseThrow(() -> new NotFoundException("Monster difficulty not found: " + request.getDifficultyId()));

        int health = request.getMaxHealth() != null ? request.getMaxHealth() : difficulty.getBaseHealth();

        monster.setName(request.getName());
        monster.setDifficulty(difficulty);
        monster.setMaxHealth(health);
        monster.setImageUrl(request.getImageUrl());

        Monster updated = monsterRepository.save(monster);
        return MonsterResponse.from(updated);
    }

    @Override
    public List<MonsterResponse> listAll() {
        return monsterRepository.findAll().stream()
                .map(MonsterResponse::from)
                .toList();
    }

    @Override
    public MonsterResponse findById(UUID id) {
        return monsterRepository.findById(id)
                .map(MonsterResponse::from)
                .orElseThrow(() -> new NotFoundException("Monster not found: " + id));
    }

    @Override
    @Transactional
    public void softDelete(UUID id) {
        Monster monster = monsterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Monster not found: " + id));
        monster.setActive(false);
        monsterRepository.save(monster);
        log.info("Monster '{}' has been deactivated", monster.getName());
    }

    // === Helpers ===

    private void validateUniqueName(String name, UUID currentId) {
        Optional<Monster> existing = monsterRepository.findAll().stream()
                .filter(m -> m.getName().equalsIgnoreCase(name) && m.isActive())
                .filter(m -> currentId == null || !m.getId().equals(currentId))
                .findFirst();

        if (existing.isPresent()) {
            throw new IllegalArgumentException("A monster with the name '" + name + "' already exists.");
        }
    }
}
