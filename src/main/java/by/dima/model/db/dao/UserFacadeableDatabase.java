package by.dima.model.db.dao;

import by.dima.model.common.UserModel;
import by.dima.model.common.route.main.Route;

public interface UserFacadeableDatabase<T> {
    UserModel authorization(UserModel user);

    UserModel authentication(UserModel user);

    boolean isAuthorization(UserModel user);

    boolean isExist(UserModel user);

    boolean validateData(UserModel user);

    boolean save(T t);
}
