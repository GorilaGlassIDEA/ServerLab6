package by.dima.model.db.dao.save;


import by.dima.model.common.route.main.Route;
import by.dima.model.common.route.sub.Coordinates;
import by.dima.model.common.route.sub.LocationFrom;
import by.dima.model.common.route.sub.LocationTo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RouteSaveService {
    private final SessionFactory sessionFactory;

    public RouteSaveService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean save(Route route) {
        //TODO: сделать сохранение данных в базу данных

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.persist(getCoordinates(route));
            session.persist(getLocationTo(route));
            session.persist(getLocationFrom(route));

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private LocationTo getLocationTo(Route route) {
        return route.getTo();
    }

    private LocationFrom getLocationFrom(Route route) {
        return route.getFrom();
    }

    private Coordinates getCoordinates(Route route) {
        return route.getCoordinates();
    }

}
