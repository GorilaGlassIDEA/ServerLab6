package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.route.model.main.Route;


/**
 * Класс реализующий команду, которая заменяет {@link Route} если значение нового Route меньше
 * это проверка происходит с помощью реализации интерфейса {@link Comparable} в Route
 */
public class ReplaceIfLoweCommand extends CommandAbstract {
    private final CollectionController collectionController;
    private final Route route;

    public ReplaceIfLoweCommand(Route route, CollectionController collectionController) {
        super("replace_if_lowe", "Replace the value by key if the new value is less than the old one.");
        this.collectionController = collectionController;
        this.route = route;
    }

    @Override
    public void execute() {
        if (collectionController.replaceRouteForKey(route)) {
            System.out.println("Your old route changed. Its id was equals " + route.getId());
        } else {
            System.out.println("Your old route didn't change.");
        }
    }
}
