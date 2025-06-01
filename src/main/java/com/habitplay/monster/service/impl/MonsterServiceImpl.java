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
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonsterServiceImpl implements MonsterService {

    private final MonsterRepository monsterRepository;

    @Override
    @Transactional
    public MonsterResponse create(MonsterRequest request) {
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
}
