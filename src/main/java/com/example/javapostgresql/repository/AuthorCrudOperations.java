package com.example.javapostgresql.repository;

import com.example.javapostgresql.model.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorCrudOperations implements CrudOperations<Author> {
    private final Connection connection;

    public AuthorCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Author> findAll() {
        String query = "SELECT * FROM author";
        List<Author> authors = new ArrayList<>();

        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                authors.add(mapResultSetToAuthor(resultSet));
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return authors;
    }

    @Override
    public List<Author> saveAll(List<Author> toSave) {
        List<Author> authors = new ArrayList<>();
        toSave.forEach(el -> {
            authors.add(save(el));
        });
        return authors;
    }

    @Override
    public Author save(Author toSave) {
        String query = "INSERT INTO author (name) VALUES (?)";
        Author author = null;

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, toSave.getAuthorName());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating author failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    toSave.setId(generatedKeys.getLong(1));
                    author = toSave;
                } else {
                    throw new SQLException("Creating author failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return author;
    }

    @Override
    public Author delete(Author toDelete) {
        String query = "DELETE FROM author WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, toDelete.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return toDelete;
    }

    private Author mapResultSetToAuthor(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getLong("id"));
        author.setAuthorName(resultSet.getString("name"));
        return author;
    }
}
