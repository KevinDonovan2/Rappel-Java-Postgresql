CREATE TABLE IF NOT EXISTS book (
    id SERIAL PRIMARY KEY,
    book_name VARCHAR(255) NOT NULL,
    page_numbers INT NOT NULL,
    topic VARCHAR(20) NOT NULL,
    release_date DATE NOT NULL,
    author_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES author(id)
    );

INSERT INTO book (book_name, page_numbers, topic, release_date, author_id) VALUES
    ('Book 1', 200, 'COMEDY', '2023-01-01', 1),
    ('Book 2', 250, 'ROMANCE', '2023-02-01', 2),
    ('Book 3', 180, 'OTHER', '2023-03-01', 1);
