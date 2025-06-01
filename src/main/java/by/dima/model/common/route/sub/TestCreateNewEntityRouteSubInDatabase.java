package by.dima.model.common.route.sub;

import by.dima.model.common.UserModel;
import by.dima.model.common.route.main.Route;
import by.dima.model.db.hibernate.config.HibernateConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.ZonedDateTime;

public class TestCreateNewEntityRouteSubInDatabase {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateConfiguration.getFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Coordinates coordinates = Coordinates.builder()
                    .x(10)
                    .y(20D)
                    .build();
            LocationTo locationTo= LocationTo.builder()
                    .name("New Location TO")
                    .y(123D)
                    .x(123D)
                    .build();
            LocationFrom locationFrom = LocationFrom.builder()
                    .name("New location from")
                    .y(1230F)
                    .x(131)
                    .build();
            Route route = Route.builder()
                    .coordinates(coordinates)
                    .to(locationTo)
                    .from(locationFrom)
                    .name("New route")
                    .distance(1203)
                    .creationDate(ZonedDateTime.now())
                    .build();

            session.persist(route);
            session.getTransaction().commit();
        }
    }
}
