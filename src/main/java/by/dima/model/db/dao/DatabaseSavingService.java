package by.dima.model.db.dao;

import by.dima.model.Main;
import by.dima.model.common.UserModel;
import by.dima.model.common.UserRouteLink;
import by.dima.model.common.route.main.Route;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class DatabaseSavingService {


    private final SessionFactory sessionFactory;
    private static Logger logger = Main.logger;

    public DatabaseSavingService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void saveRoute(UserModel userModel, Route route) {
        route.setId(null);

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            UserModel existingUserModel = session.createQuery("FROM UserModel where username= :username", UserModel.class)
                    .setParameter("username", userModel.getUsername())
                    .uniqueResult();
            if (existingUserModel != null) {
                existingUserModel.setPassword(userModel.getPassword());
                userModel = existingUserModel;
                UserRouteLink userRouteLink = UserRouteLink.builder()
                        .route(route)
                        .userModel(userModel)
                        .build();
                session.merge(userRouteLink);
            } else {
                logger.log(Level.WARNING, "Пользователя с таким username не существует! " + getClass().getName());
            }
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Ошибка при добавлении маршрута для пользователя: " + e.getMessage());
        }
    }

    public List<Route> getRoutesForUser(UserModel userModel) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            UserModel userFromDb = session.find(UserModel.class, userModel.getId());
            if (userFromDb != null) {
                if (userFromDb.equals(userModel)) {
                    logger.log(Level.FINE, "Данные совпадают! User найден");
                    return userFromDb.getRoutesList();
                } else {
                    logger.log(Level.WARNING, "ID user совпадает, но неправильный логин или пароль");
                }
            } else {
                logger.log(Level.WARNING, "User не был найден " + getClass().getName());
            }
            session.getTransaction().commit();
        }
        return null;
    }

    public List<Route> getAllRoute() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<UserModel> list = session.createQuery("FROM UserModel", UserModel.class).list();
            List<Route> allRoutesList = new ArrayList<>();
            for (UserModel userModel : list) {
                allRoutesList.addAll(userModel.getRoutesList());
            }
            session.getTransaction().commit();
            return allRoutesList;
        }
    }

    public void deleteDataForUser(UserModel userModel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            UserModel existingUserModel = session.find(UserModel.class, userModel.getId());
            existingUserModel.getUserRouteLinkList().clear();
            existingUserModel.deleteAllRouteForThisUser();

            session.getTransaction().commit();

        }
    }

    public boolean updateRoute(UserModel userModel, Route newRoute) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            UserModel existingUserModel = session.find(UserModel.class, userModel.getId());
            //TODO: переписать правильно условие с испольование StreamAPI,
            // то есть исправить сранвение по всем полям метода contains и оставить только сранвение по id
            if (existingUserModel.getRoutesList().contains(newRoute)) {
                Route exisingRouteFromDB = existingUserModel.getRoutesList().stream()
                        .filter(s -> s.getId().equals(newRoute.getId()))
                        .findFirst().get();
                exisingRouteFromDB.setName(newRoute.getName());
                logger.log(Level.INFO, "Успешное обновление Route");
            } else {
                logger.log(Level.INFO, "Не удалось обновить элемент, нет доступа!");
                return false;
            }


            session.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}
