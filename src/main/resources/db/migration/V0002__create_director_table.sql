CREATE TABLE IF NOT EXISTS director(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    nationality VARCHAR(40) NOT NULL,
    birth_date DATE

)