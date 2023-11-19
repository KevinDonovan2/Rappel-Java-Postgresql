CREATE TABLE IF NOT EXISTS subscribers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    reference VARCHAR(255) NOT NULL
);

INSERT INTO subscribers (name, reference) VALUES
    ('Subscriber 1', 'REF1'),
    ('Subscriber 2', 'REF2'),
    ('Subscriber 3', 'REF3');
