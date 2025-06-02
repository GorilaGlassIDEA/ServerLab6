package by.dima.model.db.dao;

import by.dima.model.Main;
import by.dima.model.common.UserModel;
import by.dima.model.common.UserRouteLink;
import by.dima.model.common.route.main.Route;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseSavingService {

    private static final Log log = LogFactory.getLog(DatabaseSavingService.class);
    private final SessionFactory sessionFactory;
    private static Logger logger = Main.logger;

    public DatabaseSavingService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public boolean saveRoute(UserModel userModel, Route route) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            //TODO: сделать сохранение route для конкретного
            // и сделать общие id для всех юзеров ккуuser
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Ошибка при добавлении маршрута для пользователя: " + e.getMessage());
            return false;
        }
    }

}
