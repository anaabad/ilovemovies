CREATE TABLE IF NOT EXISTS comment
(
    id          SERIAL PRIMARY KEY,
    text        VARCHAR(255) NOT NULL,
    movie_id    INT
)