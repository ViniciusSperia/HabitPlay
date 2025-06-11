CREATE TABLE habit_progress (
    id UUID PRIMARY KEY,
    habit_id UUID NOT NULL,
    user_id UUID NOT NULL,
    game_session_id UUID NOT NULL,
    progress INTEGER NOT NULL DEFAULT 0,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    completion_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
