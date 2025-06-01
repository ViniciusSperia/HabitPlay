package com.habitplay.monster.service;

import com.habitplay.monster.dto.request.MonsterRequest;
import com.habitplay.monster.dto.response.MonsterResponse;

import java.util.List;
import java.util.UUID;

public interface MonsterService {

    MonsterResponse create(MonsterRequest request);

    List<MonsterResponse> listAll();

    MonsterResponse findById(UUID id);
}
