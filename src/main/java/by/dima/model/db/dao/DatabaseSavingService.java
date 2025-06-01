package by.dima.model.db.dao;

import by.dima.model.Main;
import by.dima.model.common.UserModel;
import by.dima.model.common.route.main.Route;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseSavingService {

    private final SessionFactory sessionFactory;
    private static Logger logger = Main.logger;

    public DatabaseSavingService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void saveRoute(Route route, UserModel userModel) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(route);

            session.getTransaction().commit();
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "Id пользователя равен null при попытке сохранить Route в классе" + getClass().getName());
        }
    }

}
