package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;

/**
 * Данная команда позволяет удалить все элементы коллекции id которых меньше заданного
 */
public class RemoveLowerKeyCommand extends CommandAbstract {
    private Long keyLowe = null;
    private final CollectionController collectionController;

    public RemoveLowerKeyCommand(CollectionController collectionController) {
        super("remove_lower_key", "Remove all elements from the collection whose key is less than the specified one.");
        this.collectionController = collectionController;
    }

    @Override
    public void execute() {
        if (keyLowe != null) {
            for (int i = 0; i < keyLowe; i++) {
                collectionController.removeElem((long) i);
            }
        } else {
            System.err.println("Проверьте аргумент команды!");
        }
        System.out.println("Команда выполнена!");

    }

    @Override
    public void setArgs(String arg) {
        try {
            this.keyLowe = Long.parseLong(arg);
        } catch (NumberFormatException e) {
            this.keyLowe = null;
        }
    }
}
