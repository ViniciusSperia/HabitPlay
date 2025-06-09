CREATE TABLE habit_progress (
    id UUID PRIMARY KEY,
    habit_id UUID NOT NULL,
    user_id UUID NOT NULL,
    game_session_id UUID NOT NULL,
    progress INTEGER NOT NULL DEFAULT 0,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    completion_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_habit_progress_habit FOREIGN KEY (habit_id) REFERENCES habits(id),
    CONSTRAINT fk_habit_progress_session FOREIGN KEY (game_session_id) REFERENCES game_sessions(id),
    CONSTRAINT fk_habit_progress_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT uc_session_habit_user UNIQUE (game_session_id, habit_id, user_id)
);
