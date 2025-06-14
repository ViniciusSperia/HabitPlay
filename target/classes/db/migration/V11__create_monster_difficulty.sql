CREATE TABLE monster_difficulty (
    id UUID PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    base_health INTEGER NOT NULL,
    base_xp INTEGER NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_by UUID NOT NULL,
    updated_by UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO monster_difficulty (
    id, name, base_health, base_xp, active, created_by, created_at
) VALUES
    ('00000000-0000-0000-0000-000000000101', 'EASY',   1000,  50, TRUE, '00000000-0000-0000-0000-000000000001', NOW()),
    ('00000000-0000-0000-0000-000000000102', 'MEDIUM', 3000, 150, TRUE, '00000000-0000-0000-0000-000000000001', NOW()),
    ('00000000-0000-0000-0000-000000000103', 'HARD',   5000, 300, TRUE, '00000000-0000-0000-0000-000000000001', NOW());