package by.dima.model.db.dao;

import by.dima.model.common.UserModel;

public interface UserFacadeableDatabase {
    UserModel authorization(UserModel user);

    UserModel authentication(UserModel user);

    boolean isAuthorization(UserModel user);

    boolean isExist(UserModel user);

    boolean validateData(UserModel user);
}
