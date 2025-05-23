package by.dima.model.db;


import by.dima.model.db.dao.UserDatabaseFacade;
import by.dima.model.common.UserModel;

import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {

        UserDatabaseFacade dao = new UserDatabaseFacade();
        UserModel user = new UserModel("alex22820", "mypassword!");
        System.out.println(dao.authorization(user));

        //TODO: написать регистрацию/аутентификацию на клиента + добавить UserDTO для обмена данными
        // с полем typeRequest: authenticate/authorizex

    }
}
