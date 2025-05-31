package com.habitplay.session.controller;

import com.habitplay.session.dto.request.GameSessionRequest;
import com.habitplay.session.dto.request.GameSessionUpdateRequest;
import com.habitplay.session.dto.response.GameSessionResponse;
import com.habitplay.session.service.GameSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class GameSessionController {

    private final GameSessionService sessionService;

    @PostMapping
    public ResponseEntity<GameSessionResponse> create(@Valid @RequestBody GameSessionRequest request) {
        GameSessionResponse response = sessionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameSessionResponse> update(@PathVariable UUID id,
                                                      @Valid @RequestBody GameSessionUpdateRequest request) {
        GameSessionResponse response = sessionService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameSessionResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(sessionService.findById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<GameSessionResponse>> listMySessions() {
        return ResponseEntity.ok(sessionService.listMySessions());
    }

    @GetMapping
    public ResponseEntity<List<GameSessionResponse>> listAllActive() {
        return ResponseEntity.ok(sessionService.listAllActive());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        sessionService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/damage")
    public ResponseEntity<Void> reduceMonsterHealth(@PathVariable UUID id,
                                                    @RequestParam int amount) {
        sessionService.reduceMonsterHealth(id, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> markSessionAsCompleted(@PathVariable UUID id) {
        sessionService.markSessionAsCompleted(id);
        return ResponseEntity.ok().build();
    }
}
