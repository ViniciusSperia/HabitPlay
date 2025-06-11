package com.habitplay.difficulty.service;

import com.habitplay.difficulty.dto.request.DifficultyRequest;
import com.habitplay.difficulty.dto.response.DifficultyResponse;

import java.util.List;
import java.util.UUID;

public interface DifficultyService {

    DifficultyResponse create(DifficultyRequest request);

    List<DifficultyResponse> listAll();

    DifficultyResponse findById(UUID id);

    DifficultyResponse update(UUID id, DifficultyRequest request);

    void softDelete(UUID id);
}
