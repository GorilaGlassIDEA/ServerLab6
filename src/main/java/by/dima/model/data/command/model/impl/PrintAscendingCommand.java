package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.services.iterator.RouteIdSortIterator;

/**
 * Данная команда выводит все элементы коллекции в порядке возрастания Id
 */
public class PrintAscendingCommand extends CommandAbstract {
    private final UsersCollectionController usersCollectionController;
    private StringBuilder builder;

    public PrintAscendingCommand(UsersCollectionController usersCollectionController) {
        super("print_ascending", "Print the elements of the collection in ascending order.");
        this.usersCollectionController = usersCollectionController;
        builder = new StringBuilder();
    }

    @Override
    public void execute() {
        builder = new StringBuilder();
        CollectionController collectionController = new CollectionController(
                usersCollectionController.getCollectionDTO(getCommandDTO().getUserID())
        );

        RouteIdSortIterator iterator
                = new RouteIdSortIterator(collectionController.getCollectionForControl());
        while (iterator.hasNext()) {
            builder.append(iterator.next()).append("\n");
        }
    }

    @Override
    public String getAnswer() {

        return new String(builder);
    }
}
