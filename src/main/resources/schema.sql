CREATE TABLE IF NOT EXISTS note (
    note_id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    created_at VARCHAR(50),
    updated_at VARCHAR(50),
    owner_name VARCHAR(255),
    owner_email VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_details (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    user_role VARCHAR(255) NOT NULL
);