CREATE TABLE game_sessions (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP,
    duration VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    current_monster_health INT NOT NULL,
    created_by UUID NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    completion_date TIMESTAMP,
    monster_id UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_game_session_monster FOREIGN KEY (monster_id) REFERENCES monsters(id)
);

CREATE TABLE game_session_users (
    game_session_id UUID NOT NULL,
    user_id UUID NOT NULL,
    PRIMARY KEY (game_session_id, user_id),
    CONSTRAINT fk_game_session_user_session FOREIGN KEY (game_session_id) REFERENCES game_sessions(id),
    CONSTRAINT fk_game_session_user_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE game_session_habits (
    game_session_id UUID NOT NULL,
    habit_id UUID NOT NULL,
    PRIMARY KEY (game_session_id, habit_id),
    CONSTRAINT fk_game_session_habit_session FOREIGN KEY (game_session_id) REFERENCES game_sessions(id),
    CONSTRAINT fk_game_session_habit_habit FOREIGN KEY (habit_id) REFERENCES habits(id)
);
