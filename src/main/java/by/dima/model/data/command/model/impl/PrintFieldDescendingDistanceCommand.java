package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.common.route.main.Route;
import by.dima.model.data.services.iterator.RouteDistanceSortIterator;

/**
 * Данная команда выводит все элементы коллекции отсортированные по убыванию поля distance модели {@link Route}
 */
public class PrintFieldDescendingDistanceCommand extends CommandAbstract {
    private final UsersCollectionController usersCollectionController;
    private StringBuilder builder;

    public PrintFieldDescendingDistanceCommand(UsersCollectionController usersCollectionController) {
        super("print_field_descending_distance", "Print the values of the distance field of all elements in descending order.");
        this.usersCollectionController = usersCollectionController;
        builder = new StringBuilder();
    }

    @Override
    public void execute() {
        builder = new StringBuilder();

        CollectionController collectionController = new CollectionController(
                usersCollectionController.getCollectionDTO(getCommandDTO().getUserID())
        );

        RouteDistanceSortIterator iterator
                = new RouteDistanceSortIterator(collectionController.getCollectionForControl());
        while (iterator.hasNext()) {
            Route thisRoute = iterator.next();
            builder.append("Route c id = ").append(thisRoute.getId()).append(" имя которого: \"").append(thisRoute.getName()).append("\" - имеет дистанцию: ").append(thisRoute.getDistance()).append("\n");
        }
    }

    @Override
    public String getAnswer() {
        return new String(builder);
    }
}
