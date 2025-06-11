CREATE TABLE roles (
    id UUID PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    created_by UUID,
    updated_by UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Seeds
INSERT INTO roles (id, name, description, created_at, updated_at)
VALUES
    ('00000000-0000-0000-0000-000000000001', 'ADMIN', 'Administrador do sistema', NOW(), NOW()),
    ('00000000-0000-0000-0000-000000000002', 'USER', 'Usuário do sistema', NOW(), NOW());

