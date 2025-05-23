package by.dima.model.server.request.validate;

import by.dima.model.db.dao.UserDatabaseProxy;
import by.dima.model.db.model.UserModel;

public class ProxyDatabase implements ProxyDatabaseMethods {

    public ProxyDatabase(UserDatabaseProxy userDAO){

    }

    @Override
    public boolean isExist(String username) {
        return false;
    }

    @Override
    public UserModel getUserModel(String username) {
        return null;
    }
}
