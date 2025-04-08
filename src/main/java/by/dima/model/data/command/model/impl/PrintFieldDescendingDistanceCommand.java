package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.route.model.main.Route;
import by.dima.model.service.iterator.RouteDistanceSortIterator;

/**
 * Данная команда выводит все элементы коллекции отсортированные по убыванию поля distance модели {@link Route}
 */
public class PrintFieldDescendingDistanceCommand extends CommandAbstract {
    private final CollectionController collectionController;

    public PrintFieldDescendingDistanceCommand(CollectionController collectionController) {
        super("print_field_descending_distance", "Print the values of the distance field of all elements in descending order.");
        this.collectionController = collectionController;
    }

    @Override
    public void execute() {
        RouteDistanceSortIterator iterator
                = new RouteDistanceSortIterator(collectionController.getCollectionForControl());
        while (iterator.hasNext()) {
            Route thisRoute = iterator.next();
            System.out.println("Route with id = " + thisRoute.getId() + " which has name: \"" + thisRoute.getName() + "\" - has distance: " + thisRoute.getDistance());
        }
    }
}
