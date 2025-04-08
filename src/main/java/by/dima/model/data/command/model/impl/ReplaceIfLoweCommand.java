package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.route.model.main.CreateRouteUsingScanner;
import by.dima.model.data.route.model.main.Route;
import by.dima.model.data.route.model.main.RouteBuilder;
import by.dima.model.service.generate.id.IdGenerateble;


/**
 * Класс реализующий команду, которая заменяет {@link Route} если значение нового Route меньше
 * это проверка происходит с помощью реализации интерфейса {@link Comparable} в Route
 */
public class ReplaceIfLoweCommand extends CommandAbstract {
    private final CollectionController collectionController;
    private final CreateRouteUsingScanner routeCreator;
    private Long id;
    private final IdGenerateble idGenerateble;

    public ReplaceIfLoweCommand(IdGenerateble idGenerateble, CollectionController collectionController, CreateRouteUsingScanner routeCreator) {
        super("replace_if_lowe", "");
        this.collectionController = collectionController;
        this.routeCreator = routeCreator;
        this.idGenerateble = idGenerateble;
    }

    @Override
    public void execute() {
        Route route = routeCreator.createRoute(new RouteBuilder(), id);
        if (collectionController.replaceRouteForKey(route)) {
            System.out.println("Your old route changed. Its id was equals " + route.getId());
        } else {
            System.out.println("Your old route didn't change.");
        }
    }

    @Override
    public void setArgs(String arg) {
        try {
            id = idGenerateble.generateId(Long.parseLong(arg));
        } catch (NumberFormatException e) {
            id = idGenerateble.generateId();
        }
    }
}
