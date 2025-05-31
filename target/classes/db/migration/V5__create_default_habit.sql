CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE default_habits (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    difficulty VARCHAR(50) NOT NULL,
    target INTEGER NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Seed example
INSERT INTO default_habits (id, name, description, difficulty, target, active)
VALUES
  (gen_random_uuid(), 'Drink Water', 'Drink 2L of water during the day', 'EASY', 1, true),
  (gen_random_uuid(), 'Stretch', 'Morning stretching session', 'EASY', 1, true),
  (gen_random_uuid(), '30-Min Walk', 'Walk for 30 minutes', 'MEDIUM', 1, true),
  (gen_random_uuid(), 'No Sugar Day', 'Avoid sugar for one day', 'HARD', 1, true);
