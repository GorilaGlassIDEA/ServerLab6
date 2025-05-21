package by.dima.model.db.dao;

import by.dima.model.Main;
import by.dima.model.db.model.UserModel;
import by.dima.model.db.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    //TODO: сделать метод добавления нового юзера
    private final Logger logger;


    public UserDAO() {
        logger = Main.logger;
    }

    public void createUser(UserModel user) {
        String sqlRequest = """
                INSERT INTO users (name, password) values (?, ?)
                """;

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            int countEdit = statement.executeUpdate();
            if (countEdit>0) logger.log(Level.FINE, "Пользователь успешно добавлен!");
            else logger.log(Level.INFO, "Не удалось добавить пользователя !");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка подключения к базе данных!");
        }


    }
}
