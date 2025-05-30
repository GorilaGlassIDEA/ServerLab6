package by.dima.model.db.dao;

import by.dima.model.common.UserModel;
import by.dima.model.common.route.main.Route;
import by.dima.model.db.dao.save.RouteSaveService;
import org.hibernate.SessionFactory;


public class UserDatabaseFacade implements UserFacadeableDatabase<Route> {
    private final AuthorizationService authorizationService;
    private final AuthenticationService authenticationService;
    private final RouteSaveService routeSaveService;

    public UserDatabaseFacade(SessionFactory sessionFactory) {
        authorizationService = new AuthorizationService(sessionFactory);
        authenticationService = new AuthenticationService(sessionFactory);
        routeSaveService = new RouteSaveService(sessionFactory);
    }

    /**
     * Данный метод выполняет авторизацию пользователей в базе данных.
     * На вход приходит потенциальный user, а на выходе либо тот же user с правильным Id,
     * либо пустой null, так как пользователь не был найден!
     *
     * @param user
     * @return
     */

    public UserModel authorization(UserModel user) {
        return authorizationService.authorization(user);
    }

    /**
     * Данный метод возвращает UserModel с id из базы данных сгенерированный для добавленного пользователя в случае успеха
     * или null в случае ошибки или исключения
     *
     * @param user
     * @return
     */
    public UserModel authentication(UserModel user) {
        return authenticationService.authentication(user);
    }

    /**
     * Данный метод проверяет авторизован ли пользователь проверяя метод авторизации на null
     *
     * @param user
     * @return
     */
    public boolean isAuthorization(UserModel user) {
        return authorizationService.isExist(user);
    }

    /**
     * Данный метод проверят зарегистрирован ли пользователь
     */
    public boolean isExist(UserModel user) {
        return authenticationService.isExist(user);
    }

    public boolean validateData(UserModel user) {
        //TODO: написать проверку данных на корректность
        return true;
    }

    public boolean save(Route route) {
        return routeSaveService.save(route);
    }
}
