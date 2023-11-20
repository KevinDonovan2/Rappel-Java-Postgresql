CREATE TABLE IF NOT EXISTS topic (
     id SERIAL PRIMARY KEY,
     name VARCHAR(255) NOT NULL
);

INSERT INTO topic (name) VALUES
    ('COMEDY'),
    ('ROMANCE'),
    ('OTHER');
