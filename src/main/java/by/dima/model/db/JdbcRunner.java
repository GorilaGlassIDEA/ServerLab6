package by.dima.model.db;

import by.dima.model.db.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {

        String sql = """
                SELECT * FROM users;
                """;

        try (Connection connection = ConnectionManager.open()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                int id =result.getInt("id");
                System.out.println(id);
            }
        }
    }
}
