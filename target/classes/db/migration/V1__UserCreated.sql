CREATE TABLE users (
    id UUID PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    profile_image_url TEXT,
    last_login_at TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE,

    role_id UUID NOT NULL,
    created_by UUID,
    updated_by UUID,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
