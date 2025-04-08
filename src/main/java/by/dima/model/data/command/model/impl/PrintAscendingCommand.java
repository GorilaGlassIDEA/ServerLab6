package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.service.iterator.RouteIdSortIterator;

/**
 * Данная команда выводит все элементы коллекции в порядке возрастания Id
 */
public class PrintAscendingCommand extends CommandAbstract {
    private final CollectionController collectionController;

    public PrintAscendingCommand(CollectionController collectionController) {
        super("print_ascending", "Print the elements of the collection in ascending order.");
        this.collectionController = collectionController;
    }

    @Override
    public void execute() {

        RouteIdSortIterator iterator
                = new RouteIdSortIterator(collectionController.getCollectionForControl());

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}
