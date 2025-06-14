CREATE TABLE difficulty (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_by UUID NOT NULL,
    updated_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

INSERT INTO difficulty (id, name, active, created_by, created_at)
VALUES
    ('00000000-0000-0000-0000-000000000011', 'EASY', TRUE, '00000000-0000-0000-0000-000000000001', CURRENT_TIMESTAMP),
    ('00000000-0000-0000-0000-000000000012', 'MEDIUM', TRUE, '00000000-0000-0000-0000-000000000001', CURRENT_TIMESTAMP),
    ('00000000-0000-0000-0000-000000000013', 'HARD', TRUE, '00000000-0000-0000-0000-000000000001', CURRENT_TIMESTAMP);
