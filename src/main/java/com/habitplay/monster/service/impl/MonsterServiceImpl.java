package com.habitplay.monster.service.impl;

import com.habitplay.config.exception.NotFoundException;
import com.habitplay.monster.dto.request.MonsterRequest;
import com.habitplay.monster.dto.response.MonsterResponse;
import com.habitplay.monster.model.Monster;
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
@Service
@RequiredArgsConstructor
public class MonsterServiceImpl implements MonsterService {

    private final MonsterRepository monsterRepository;

    @Override
    @Transactional
    public MonsterResponse create(MonsterRequest request) {
        validateUniqueName(request.getName(), null);

        Monster monster = Monster.builder()
                .name(request.getName())
                .difficulty(request.getDifficulty())
                .maxHealth(
                        request.getMaxHealth() != null
                                ? request.getMaxHealth()
                                : request.getDifficulty().getDefaultMaxHealth()
                )
                .damagePerHabit(request.getDamagePerHabit())
                .imageUrl(request.getImageUrl())
                .active(true)
                .build();

        if (request.getMaxHealth() == null) {
            log.info("No maxHealth provided, using default for difficulty {}", request.getDifficulty());
        }

        Monster saved = monsterRepository.save(monster);
        log.info("Monster '{}' created with difficulty {}", saved.getName(), saved.getDifficulty());
        return MonsterResponse.from(saved);
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
    public MonsterResponse update(UUID id, MonsterRequest request) {
        Monster monster = monsterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Monster not found: " + id));

        validateUniqueName(request.getName(), id);

        monster.setName(request.getName());
        monster.setDifficulty(request.getDifficulty());
        monster.setMaxHealth(request.getMaxHealth() != null
                ? request.getMaxHealth()
                : request.getDifficulty().getDefaultMaxHealth());
        monster.setDamagePerHabit(request.getDamagePerHabit());
        monster.setImageUrl(request.getImageUrl());

        Monster updated = monsterRepository.save(monster);
        return MonsterResponse.from(updated);
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
