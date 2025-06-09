package com.habitplay.session.service;

import com.habitplay.session.dto.request.GameSessionRequest;
import com.habitplay.session.dto.request.GameSessionUpdateRequest;
import com.habitplay.session.dto.response.GameSessionResponse;
import com.habitplay.session.dto.response.GameSessionSummaryResponse;

import java.util.List;
import java.util.UUID;

public interface GameSessionService {

    GameSessionResponse create(GameSessionRequest request);

    GameSessionResponse update(UUID id, GameSessionUpdateRequest request);

    GameSessionResponse findById(UUID id);

    List<GameSessionResponse> listAllActive();

    List<GameSessionSummaryResponse> listMySessions();

    void deactivate(UUID id);

    void reduceMonsterHealth(UUID id, int amount);

    void markSessionAsCompleted(UUID id);

    GameSessionSummaryResponse findSummaryById(UUID id);

    void checkAndCloseExpiredSessions();
}
