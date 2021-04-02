CREATE TABLE IF NOT EXISTS movie(
    id SERIAL PRIMARY KEY,
    name VARCHAR(250),
    release_date DATE,
    duration INTEGER,
    genre VARCHAR(255)
)