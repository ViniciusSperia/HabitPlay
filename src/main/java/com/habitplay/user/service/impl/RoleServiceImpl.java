package com.habitplay.user.service.impl;

import com.habitplay.config.exception.NotFoundException;
import com.habitplay.user.model.Role;
import com.habitplay.user.repository.RoleRepository;
import com.habitplay.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findById(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found: " + id));
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Role not found: " + name));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(UUID id, Role updated) {
        Role existing = findById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        return roleRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        roleRepository.deleteById(id);
    }
}

