package com.example.javapostgresql.repository;

import com.example.javapostgresql.model.Subscriber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscribersCrudOperations implements CrudOperations<Subscriber> {
    private final Connection connection;

    public SubscribersCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Subscriber> findAll() {
        String query = "SELECT * FROM subscribers";
        List<Subscriber> subscribersList = new ArrayList<>();

        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                subscribersList.add(mapResultSetToSubscriber(resultSet));
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return subscribersList;
    }

    @Override
    public List<Subscriber> saveAll(List<Subscriber> toSave) {
        List<Subscriber> subscribersList = new ArrayList<>();
        toSave.forEach(el -> {
            subscribersList.add(save(el));
        });
        return subscribersList;
    }

    @Override
    public Subscriber save(Subscriber toSave) {
        String query = "INSERT INTO subscribers (user_id, user_name, password, email) VALUES (?, ?, ?, ?)";
        Subscriber subscriber = null;

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, toSave.getUserId());
            statement.setString(2, toSave.getUserName());
            statement.setString(3, toSave.getPassword());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating subscriber failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    toSave.setUserId((int) generatedKeys.getLong(1));
                    subscriber = toSave;
                } else {
                    throw new SQLException("Creating subscriber failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return subscriber;
    }

    @Override
    public Subscriber delete(Subscriber toDelete) {
        String query = "DELETE FROM subscribers WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, toDelete.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return toDelete;
    }

    private Subscriber mapResultSetToSubscriber(ResultSet resultSet) throws SQLException {
        Subscriber subscriber = new Subscriber(
                resultSet.getInt("user_id"),
                resultSet.getString("user_name"),
                resultSet.getString("password")
        );
        subscriber.setUserId((int) resultSet.getLong("id"));
        return subscriber;
    }
}
