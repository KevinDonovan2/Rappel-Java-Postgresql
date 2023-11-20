package com.example.javapostgresql.connection;

import com.example.javapostgresql.repository.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement du driver PostgreSQL");
            e.printStackTrace();
            return;
        }

        String jdbcUrl = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
            System.out.println("Connexion à la base de données réussie !");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données");
            e.printStackTrace();
        }
    }
    private static Connection connection;
    public static Connection getConnection(){
        if(connection != null)
            return connection;

        try{
            connection = DriverManager.getConnection(
                    Db.DB_URL,
                    Db.DB_USERNAME,
                    Db.DB_PASSWORD
            );
            return connection;
        }
        catch (SQLException error){
            System.out.println(error.getMessage());
            throw new RuntimeException("Connection failed");
        }
    }
}