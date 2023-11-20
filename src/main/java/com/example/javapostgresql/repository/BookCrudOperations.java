package com.example.javapostgresql.repository;

import com.example.javapostgresql.model.Book;
import com.example.javapostgresql.model.Author;
import com.example.javapostgresql.model.Topic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookCrudOperations implements CrudOperations<Book> {
    private final Connection connection;

    public BookCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM book";
        List<Book> books = new ArrayList<>();

        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                books.add(mapResultSetToBook(resultSet));
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> saveAll(List<Book> toSave) {
        List<Book> books = new ArrayList<>();
        toSave.forEach(el -> {
            books.add(save(el));
        });
        return books;
    }

    @Override
    public Book save(Book toSave) {
        String query = "INSERT INTO book (book_name, page_numbers, topic, release_date, author_id) VALUES (?, ?, ?, ?, ?)";
        Book book = null;

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, toSave.getBookName());
            statement.setLong(2, toSave.getPageNumbers());
            statement.setString(3, String.valueOf(toSave.getTopic()));
            statement.setDate(4, Date.valueOf(toSave.getReleaseDate()));
            statement.setLong(5, toSave.getAuthorId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    toSave.setId(Long.valueOf(generatedKeys.getString(1)));
                    book = toSave;
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return book;
    }

    @Override
    public Book delete(Book toDelete) {
        String query = "DELETE FROM book WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, String.valueOf(toDelete.getId()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return toDelete;
    }
    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setBookName(resultSet.getString("book_name"));
        book.setPageNumbers(resultSet.getInt("page_numbers"));
        book.setTopic(Topic.valueOf(resultSet.getString("topic")));
        book.setReleaseDate(resultSet.getDate("release_date").toLocalDate());
        book.setAuthorId(resultSet.getLong("author_id"));
        return book;
    }
}
