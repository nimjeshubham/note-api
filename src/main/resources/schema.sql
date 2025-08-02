CREATE TABLE IF NOT EXISTS note (
    note_id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    created_at VARCHAR(50),
    updated_at VARCHAR(50),
    owner_name VARCHAR(255),
    owner_email VARCHAR(255)
);