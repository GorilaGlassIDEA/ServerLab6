package by.dima.model.db.hibernate.config;

import by.dima.model.common.UserModel;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfiguration {
    private static SessionFactory sessionFactory;

    public static SessionFactory getFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(UserModel.class);
        sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
