ALTER TABLE director
    ADD COLUMN created_at DATE,
    ADD COLUMN modified_at DATE;

ALTER TABLE actor
    ADD COLUMN created_at DATE,
    ADD COLUMN modified_at DATE;