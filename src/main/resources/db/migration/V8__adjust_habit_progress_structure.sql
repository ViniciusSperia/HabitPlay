-- Remove duplicate column
ALTER TABLE habit_progress
DROP COLUMN IF EXISTS progress;

ALTER TABLE habit_progress
ADD CONSTRAINT uc_habit_progress_session_habit UNIQUE (session_id, habit_id);