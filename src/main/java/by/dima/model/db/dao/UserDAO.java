package by.dima.model.db.dao;

import by.dima.model.Main;
import by.dima.model.db.model.UserModel;
import by.dima.model.db.utils.ConnectionManager;

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
     * Данный метод возвращает id сгенерированный для добавленного пользователя в случае успеха
     * или -1 в случае ошибки или исключения
     *
     * @param user
     * @return
     */
    public int createUser(UserModel user) {
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
                return result.getInt("id");
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
        return -1;
    }
}
