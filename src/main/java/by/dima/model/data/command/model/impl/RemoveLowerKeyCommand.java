package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;

/**
 * Данная команда позволяет удалить все элементы коллекции id которых меньше заданного
 */
public class RemoveLowerKeyCommand extends CommandAbstract {

    private final UsersCollectionController usersCollectionController;
    private StringBuilder builder;

    public RemoveLowerKeyCommand(UsersCollectionController usersCollectionController) {
        super("remove_lower_key", "Remove all elements from the collection whose key is less than the specified one.");
        this.usersCollectionController = usersCollectionController;
        builder = new StringBuilder();
    }

    @Override
    public void execute() {
        builder = new StringBuilder();
        System.out.println(getCommandDTO());
        if (getCommandDTO().getArgCommand() != null) {
            long keyLowe = Long.parseLong(getCommandDTO().getArgCommand());
            CollectionController collectionController = new CollectionController(usersCollectionController.getCollectionDTO(getCommandDTO().getUserID()));
            for (int i = 0; i < keyLowe; i++) {
                collectionController.removeElem((long) i);
            }
        } else {
            builder.append("Проверьте аргумент команды!");
        }
        builder.append("Команда выполнена!");
    }

    @Override
    public String getAnswer() {
        return new String(builder);
    }
}
