CREATE TABLE habits (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    difficulty VARCHAR(20) NOT NULL,
    target INTEGER NOT NULL CHECK (target >= 1),
    active BOOLEAN DEFAULT TRUE,
    user_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT uq_user_name UNIQUE (user_id, name)
);
