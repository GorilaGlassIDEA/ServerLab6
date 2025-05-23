package by.dima.model.db.dao;

import by.dima.model.Main;
import by.dima.model.db.model.UserModel;
import by.dima.model.db.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorizationService {
    private final static Logger logger = Main.logger;

    public static UserModel authorization(UserModel user) {
        UserModel answerUser = new UserModel();
        String sqlRequest = """
                SELECT id, username, name, password from users where username = ?;
                """;

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setString(1, user.getUsername());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                answerUser.setId(result.getInt("id"));
                answerUser.setUsername(result.getString("username"));
                answerUser.setName(result.getString("name"));
                answerUser.setPassword(result.getString("password"));
                return answerUser;
            } else {
                logger.log(Level.WARNING, "Пользователь с username: " + user.getUsername() + " не найден!");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка подключения к базе данных!");
        }
        return null;
    }

}
