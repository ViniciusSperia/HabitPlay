package com.habitplay.user.service;

import com.habitplay.user.model.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    Role findById(UUID id);

    Role findByName(String name);

    List<Role> findAll();

    Role create(Role role);

    Role update(UUID id, Role updated);

    void delete(UUID id);
}
