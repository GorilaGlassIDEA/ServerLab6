package by.dima.model.db;


import by.dima.model.db.dao.UserDAO;
import by.dima.model.db.model.UserModel;

import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {

        UserDAO dao = new UserDAO();
        UserModel user = new UserModel("alex22820", "Dima", "mypassword!");
        System.out.println("Ключ, сгенерированный для пользователя: " + dao.createUser(user));

    }
}
