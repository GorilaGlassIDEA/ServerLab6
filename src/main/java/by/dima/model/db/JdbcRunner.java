package by.dima.model.db;

import by.dima.model.db.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {

        try (Connection connection = ConnectionManager.open()) {
            System.out.println( connection.getTransactionIsolation());
        }
    }
}
