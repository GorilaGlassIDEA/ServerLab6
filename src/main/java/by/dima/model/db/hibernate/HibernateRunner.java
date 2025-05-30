package by.dima.model.db.hibernate;

import by.dima.model.common.UserModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateRunner {
    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(UserModel.class);

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(UserModel.builder()
                    .id(1)
                    .username("Pratice")
                    .password("Hibernate")
                    .build());

            session.getTransaction().commit();

        }
    }
}
