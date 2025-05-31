CREATE TABLE habit_progress (
    id UUID PRIMARY KEY,
    habit_id UUID NOT NULL,
    session_id UUID NOT NULL,
    current_progress INTEGER NOT NULL DEFAULT 0,
    progress INTEGER NOT NULL DEFAULT 0,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    completion_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_habit_progress_habit FOREIGN KEY (habit_id) REFERENCES habits(id),
    CONSTRAINT fk_habit_progress_session FOREIGN KEY (session_id) REFERENCES game_sessions(id)
);
