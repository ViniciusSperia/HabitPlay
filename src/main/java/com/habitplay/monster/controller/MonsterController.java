package com.habitplay.monster.controller;

import com.habitplay.monster.dto.request.MonsterRequest;
import com.habitplay.monster.dto.response.MonsterResponse;
import com.habitplay.monster.service.MonsterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MonsterController {

    private final MonsterService monsterService;

    @PostMapping("/admin/monsters")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MonsterResponse> create(@Valid @RequestBody MonsterRequest request) {
        return ResponseEntity.ok(monsterService.create(request));
    }

    @GetMapping("/public/monsters")
    public ResponseEntity<List<MonsterResponse>> listAll() {
        return ResponseEntity.ok(monsterService.listAll());
    }

    @GetMapping("/public/monsters/{id}")
    public ResponseEntity<MonsterResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(monsterService.findById(id));
    }
}
