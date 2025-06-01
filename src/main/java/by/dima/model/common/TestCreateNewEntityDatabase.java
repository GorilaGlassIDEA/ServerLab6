package by.dima.model.common;

import by.dima.model.common.route.main.Route;
import by.dima.model.common.route.sub.Coordinates;
import by.dima.model.common.route.sub.LocationFrom;
import by.dima.model.common.route.sub.LocationTo;
import by.dima.model.db.hibernate.config.HibernateConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.ZonedDateTime;

public class TestCreateNewEntityDatabase {
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

            UserModel userModel = new UserModel("dima","Crazy");

            UserRouteLink userRouteLink = UserRouteLink.builder()
                    .route(route)
                    .userModel(userModel)
                    .build();

            session.persist(userRouteLink);
            session.getTransaction().commit();
        }
    }
}
