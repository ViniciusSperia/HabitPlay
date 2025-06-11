package com.habitplay.difficulty.service.impl;

import com.habitplay.config.exception.NotFoundException;
import com.habitplay.difficulty.dto.request.DifficultyRequest;
import com.habitplay.difficulty.dto.response.DifficultyResponse;
import com.habitplay.difficulty.model.Difficulty;
import com.habitplay.difficulty.repository.DifficultyRepository;
import com.habitplay.difficulty.service.DifficultyService;
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
public class DifficultyServiceImpl implements DifficultyService {

    private final DifficultyRepository difficultyRepository;

    @Override
    @Transactional
    public DifficultyResponse create(DifficultyRequest request) {
        validateUniqueName(request.getName(), null);

        Difficulty difficulty = Difficulty.builder()
                .name(request.getName())
                .active(true)
                .build();

        Difficulty saved = difficultyRepository.save(difficulty);
        log.info("Created difficulty {}", saved.getName());
        return DifficultyResponse.from(saved);
    }

    @Override
    public List<DifficultyResponse> listAll() {
        return difficultyRepository.findAll().stream()
                .map(DifficultyResponse::from)
                .toList();
    }

    @Override
    public DifficultyResponse findById(UUID id) {
        return difficultyRepository.findById(id)
                .map(DifficultyResponse::from)
                .orElseThrow(() -> new NotFoundException("Difficulty not found: " + id));
    }

    @Override
    @Transactional
    public DifficultyResponse update(UUID id, DifficultyRequest request) {
        Difficulty difficulty = difficultyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Difficulty not found: " + id));

        validateUniqueName(request.getName(), id);
        difficulty.setName(request.getName());

        Difficulty updated = difficultyRepository.save(difficulty);
        return DifficultyResponse.from(updated);
    }

    @Override
    @Transactional
    public void softDelete(UUID id) {
        Difficulty difficulty = difficultyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("difficulty not found: " + id));
        difficulty.setActive(false);
        difficultyRepository.save(difficulty);
        log.info("difficulty '{}' has been deactivated", difficulty.getName());
    }

    // === Helpers ===

    private void validateUniqueName(String name, UUID currentId) {
        Optional<Difficulty> existing = difficultyRepository.findAll().stream()
                .filter(m -> m.getName().equalsIgnoreCase(name) && m.isActive())
                .filter(m -> m.getId().equals(currentId))
                .findFirst();

        if (existing.isPresent()) {
            throw new IllegalArgumentException("A difficulty with the name '" + name + "' already exists.");
        }
    }
}
