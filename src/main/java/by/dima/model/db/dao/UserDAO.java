package by.dima.model.db.dao;

import by.dima.model.Main;
import by.dima.model.db.model.UserModel;
import by.dima.model.db.utils.ConnectionManager;

import java.net.http.HttpConnectTimeoutException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    //TODO: сделать метод добавления нового юзера
    private final Logger logger;


    public UserDAO() {
        logger = Main.logger;
    }

    /**
     * Данный метод выполняет авторизацию пользователей в базе данных.
     * На вход приходит потенциальный user, а на выходе либо тот же user с правильным Id,
     * либо пустой user, так как пользователь не был найден!
     *
     * @param user
     * @return
     */

    public UserModel authorization(UserModel user) {
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
            } else {
                logger.log(Level.WARNING, "Пользователь с username: " + user.getUsername() + " не найден!");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка подключения к базе данных!");
        }
        return answerUser;
    }

    /**
     * Данный метод возвращает id сгенерированный для добавленного пользователя в случае успеха
     * или -1 в случае ошибки или исключения
     *
     * @param user
     * @return
     */
    public UserModel authentication(UserModel user) {
        String sqlRequest = """
                INSERT INTO users (username, name, password) values (?,?, ?)
                """;

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            int countEdit = statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (countEdit > 0 && result.next()) {
                logger.log(Level.FINE, "Пользователь успешно добавлен!");
                user.setId(result.getInt("id"));
                return user;
            } else {
                logger.log(Level.INFO, "Не удалось добавить пользователя !");
            }
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                logger.log(Level.WARNING, "Пользователь с таким именем уже существует!");
                //TODO добавлять в ответ от сервера информацию о том, что такой ник уже существует!
            } else {
                logger.log(Level.SEVERE, "Ошибка подключения к базе данных!");
            }
        }
        return user;
    }
}
