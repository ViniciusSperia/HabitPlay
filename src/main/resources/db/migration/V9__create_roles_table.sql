CREATE TABLE roles (
    id UUID PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    created_by UUID,
    updated_by UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

        CONSTRAINT fk_roles_created_by FOREIGN KEY (created_by) REFERENCES users(id),
        CONSTRAINT fk_roles_updated_by FOREIGN KEY (updated_by) REFERENCES users(id)
);


-- Inserção inicial de roles (recomendado fixar os UUIDs para uso em usuários iniciais)
INSERT INTO roles (id, name, description, created_at, updated_at)
VALUES
    ('00000000-0000-0000-0000-000000000001', 'ADMIN', 'Administrador do sistema', NOW(), NOW()),
    ('00000000-0000-0000-0000-000000000002', 'USER', 'Usuário do sistema', NOW(), NOW());
