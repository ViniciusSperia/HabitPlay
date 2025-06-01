package com.habitplay.session.service;

import com.habitplay.session.dto.request.GameSessionRequest;
import com.habitplay.session.dto.request.GameSessionUpdateRequest;
import com.habitplay.session.dto.response.GameSessionResponse;
import com.habitplay.session.dto.response.GameSessionSummaryResponse;

import java.util.List;
import java.util.UUID;

public interface GameSessionService {

    GameSessionResponse create(GameSessionRequest request);

    GameSessionSummaryResponse findSummaryById(UUID sessionId);
    GameSessionResponse update(UUID sessionId, GameSessionUpdateRequest request);
    GameSessionResponse findById(UUID sessionId);
    List<GameSessionResponse> listMySessions();
    List<GameSessionResponse> listAllActive();
    void deactivate(UUID sessionId);
    void reduceMonsterHealth(UUID sessionId, int amount);
    void markSessionAsCompleted(UUID sessionId);
    void checkAndCloseExpiredSessions();
}
