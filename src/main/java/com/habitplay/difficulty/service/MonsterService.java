package com.habitplay.difficulty.service;

import com.habitplay.monster.dto.request.MonsterRequest;
import com.habitplay.monster.dto.response.MonsterResponse;

import java.util.List;
import java.util.UUID;

public interface MonsterService {

    MonsterResponse create(MonsterRequest request);

    List<MonsterResponse> listAll();

    MonsterResponse findById(UUID id);

    MonsterResponse update(UUID id, MonsterRequest request);

    void softDelete(UUID id);
}
