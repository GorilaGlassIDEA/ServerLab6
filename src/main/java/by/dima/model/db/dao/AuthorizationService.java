package by.dima.model.db.dao;

import by.dima.model.Main;
import by.dima.model.common.UserModel;
import by.dima.model.db.utils.ConnectionManager;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

class AuthorizationService {
    private final static Logger logger = Main.logger;
    private final SessionFactory sessionFactory;

    public AuthorizationService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserModel authorization(UserModel user) {
        //TODO: Переписать под hibernate
        UserModel answerUser = new UserModel();
        String sqlRequest = """
                SELECT id, username, password from users where username = ?;
                """;

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setString(1, user.getUsername());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                answerUser.setId(result.getInt("id"));
                answerUser.setUsername(result.getString("username"));
                answerUser.setPassword(result.getString("password"));
                logger.log(Level.FINE, "Пользователь найден в базе данных!");
                return answerUser;
            } else {
                logger.log(Level.WARNING, "Пользователь с username: " + user.getUsername() + " не найден!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Ошибка подключения к базе данных!");
        }
        return new UserModel();
    }

    public boolean isExist(UserModel userModel) {
        //TODO: Переписать под hibernate
        String sqlRequest = """
                SELECT COUNT(*) FROM users WHERE username=? and password=?
                """;
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setString(1, userModel.getUsername());
            statement.setString(2, userModel.getPassword());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Ошибка подключения к базе данных!");
        }
        return false;
    }

}
