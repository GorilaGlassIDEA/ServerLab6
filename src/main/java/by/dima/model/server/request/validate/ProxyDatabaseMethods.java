package by.dima.model.server.request.validate;

import by.dima.model.db.model.UserModel;

public interface ProxyDatabaseMethods {
    /**
     * Проверяет существует ли пользователь с таким username
     *
     * @param username
     * @return boolean
     */
    boolean isExist(String username);

    UserModel getUserModel(String username);
}
