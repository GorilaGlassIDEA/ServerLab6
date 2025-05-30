package by.dima.model.db.dao;

import by.dima.model.Main;
import by.dima.model.common.UserModel;
import by.dima.model.db.utils.ConnectionManager;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class AuthenticationService {
    private final static Logger logger = Main.logger;
    private final SessionFactory sessionFactory;

    public AuthenticationService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    /**
     * Данный метод возвращает id сгенерированный для добавленного пользователя в случае успеха
     * или -1 в случае ошибки или исключения
     *
     * @param user
     * @return
     */
    public UserModel authentication(UserModel user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.flush();
            session.getTransaction().commit();
            logger.log(Level.FINE, "Пользователь успешно создан!");
            return user;
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                String sqlState = ((ConstraintViolationException) e.getCause()).getSQLState();
                if ("23505".equals(sqlState)) {
                    logger.log(Level.WARNING, "Пользователь с таким именем уже существует!");
                } else {
                    logger.log(Level.SEVERE, "Нарушение ограничения базы данных: " + sqlState, e);
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Неизвестная ошибка при выполнении запросов к базе!");
        }
        return null;
    }

    public boolean isExist(UserModel user) {
        //TODO: реализоваь через Hibernate
        String sql = """
                SELECT COUNT(*) FROM users WHERE  username=?
                """;
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return !(result.getInt(1) == 0);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка подключения к базе данных!");
        }
        return false;
    }
}
