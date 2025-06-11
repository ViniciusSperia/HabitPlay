-- HABITS
ALTER TABLE habits
    ADD CONSTRAINT fk_habits_difficulty FOREIGN KEY (difficulty_id) REFERENCES difficulty(id),
    ADD CONSTRAINT fk_habits_user FOREIGN KEY (user_id) REFERENCES users(id),
    ADD CONSTRAINT fk_habits_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    ADD CONSTRAINT fk_habits_updated_by FOREIGN KEY (updated_by) REFERENCES users(id);

-- GAME_SESSIONS
ALTER TABLE game_sessions
    ADD CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    ADD CONSTRAINT fk_game_session_monster FOREIGN KEY (monster_id) REFERENCES monsters(id);

-- GAME_SESSION_USERS
ALTER TABLE game_session_users
    ADD PRIMARY KEY (game_session_id, user_id),
    ADD CONSTRAINT fk_game_session_user_session FOREIGN KEY (game_session_id) REFERENCES game_sessions(id),
    ADD CONSTRAINT fk_game_session_user_user FOREIGN KEY (user_id) REFERENCES users(id);

-- GAME_SESSION_HABITS
ALTER TABLE game_session_habits
    ADD PRIMARY KEY (game_session_id, habit_id),
    ADD CONSTRAINT fk_game_session_habit_session FOREIGN KEY (game_session_id) REFERENCES game_sessions(id),
    ADD CONSTRAINT fk_game_session_habit_habit FOREIGN KEY (habit_id) REFERENCES habits(id);

-- HABIT_PROGRESS
ALTER TABLE habit_progress
    ADD CONSTRAINT fk_habit_progress_habit FOREIGN KEY (habit_id) REFERENCES habits(id),
    ADD CONSTRAINT fk_habit_progress_session FOREIGN KEY (game_session_id) REFERENCES game_sessions(id),
    ADD CONSTRAINT fk_habit_progress_user FOREIGN KEY (user_id) REFERENCES users(id),
    ADD CONSTRAINT uc_session_habit_user UNIQUE (game_session_id, habit_id, user_id);

-- MONSTERS
ALTER TABLE monsters
    ADD CONSTRAINT fk_monsters_difficulty FOREIGN KEY (difficulty_id) REFERENCES monster_difficulty(id);

-- USERS x ROLES
ALTER TABLE users
    ADD CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES roles(id),
    ADD CONSTRAINT fk_users_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    ADD CONSTRAINT fk_users_updated_by FOREIGN KEY (updated_by) REFERENCES users(id);

ALTER TABLE roles
    ADD CONSTRAINT fk_roles_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    ADD CONSTRAINT fk_roles_updated_by FOREIGN KEY (updated_by) REFERENCES users(id);
